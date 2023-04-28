var express = require("express");
var router = express.Router();
const { v4: uuidv4 } = require('uuid');
var jwt = require('jsonwebtoken');

var MongoClient = require("mongodb").MongoClient;
var ObjectId = require("mongodb").ObjectId;
var url =
  "mongodb://wednesday:QeaaM9OHbl2HagsCTriOyABOboMjuJkRCwQv2HzJx72pPvBqjm3v0BppZ9Xe6CiNdwwdwGh2f0touRLrhxgJXg==@wednesday.mongo.cosmos.azure.com:10255/?ssl=true&retrywrites=false&maxIdleTimeMS=120000&appName=@wednesday@";

var db;

/* GET home page. */
MongoClient.connect(url, function (err, client) {
  db = client.db("bookingsDB");
  console.log("DB connected");
});

/* Ajax-Books-Pagination */
router.get("/api/books", async function (req, res) {
  var perPage = Math.max(req.query.perPage, 6) || 6;
  var currentPage = req.query.page || 1;
  var results = await db
    .collection("books")
    .find(
      {},
      {
        limit: perPage,
        skip: perPage * (Math.max(req.query.page - 1, 0) || 0),
      }
    )
    .toArray();

  var pages = Math.ceil((await db.collection("books").count()) / perPage);

  // return res.render('paginate', { bookings: results, pages: pages, perPage: perPage });

  return res.json({ books: results, pages: pages, currentPage: currentPage });
});

/* Ajax-Games-Pagination */
router.get("/api/games", async function (req, res) {
  var perPage = Math.max(req.query.perPage, 6) || 6;
  var currentPage = req.query.page || 1;
  var results = await db
    .collection("games")
    .find(
      {},
      {
        limit: perPage,
        skip: perPage * (Math.max(req.query.page - 1, 0) || 0),
      }
    )
    .toArray();

  var pages = Math.ceil((await db.collection("games").count()) / perPage);

  // return res.render('paginate', { bookings: results, pages: pages, perPage: perPage });

  return res.json({ games: results, pages: pages, currentPage: currentPage });
});

/* Ajax-Gifts-Pagination */
router.get("/api/gifts", async function (req, res) {
  var perPage = Math.max(req.query.perPage, 6) || 6;
  var currentPage = req.query.page || 1;
  var results = await db
    .collection("gifts")
    .find(
      {},
      {
        limit: perPage,
        skip: perPage * (Math.max(req.query.page - 1, 0) || 0),
      }
    )
    .toArray();

  var pages = Math.ceil((await db.collection("gifts").count()) / perPage);

  // return res.render('paginate', { bookings: results, pages: pages, perPage: perPage });

  return res.json({ gifts: results, pages: pages, currentPage: currentPage });
});
/* Ajax-Materials-Pagination */
router.get("/api/materials", async function (req, res) {
  var perPage = Math.max(req.query.perPage, 6) || 6;
  var currentPage = req.query.page || 1;
  var results = await db
    .collection("materials")
    .find(
      {},
      {
        limit: perPage,
        skip: perPage * (Math.max(req.query.page - 1, 0) || 0),
      }
    )
    .toArray();

  var pages = Math.ceil((await db.collection("materials").count()) / perPage);

  // return res.render('paginate', { bookings: results, pages: pages, perPage: perPage });

  return res.json({
    materials: results,
    pages: pages,
    currentPage: currentPage,
  });
});

/* Ajax-Materials-Pagination */
router.get("/api/search", async function (req, res) {
  var whereClause = {};
  var perPage = Math.max(req.query.perPage, 6) || 6;
  var currentPage = req.query.page || 1;
  if (req.query.title) whereClause.title = { $regex: req.query.title };
  var booksresults = await db
    .collection("books")
    .find(whereClause, {
      limit: perPage,
      skip: perPage* (Math.max(req.query.page - 1, 0) || 0),
    })
    .toArray();
  var gamesresults = await db
    .collection("games")
    .find(whereClause, {
      limit: perPage,
      skip: perPage * (Math.max(req.query.page - 1, 0) || 0),
    })
    .toArray();
  var giftsresults = await db
    .collection("gifts")
    .find(whereClause, {
      limit: perPage,
      skip: perPage* (Math.max(req.query.page - 1, 0) || 0),
    })
    .toArray();
  var materialsresults = await db
    .collection("materials")
    .find(whereClause, {
      limit: perPage,
      skip: perPage * (Math.max(req.query.page - 1, 0) || 0),
    })
    .toArray();
  let results = booksresults
    .concat(gamesresults)
    .concat(giftsresults)
    .concat(materialsresults);
  console.log(results);
  var pages = Math.ceil(
    ((await db.collection("books").find(whereClause).count()) +
      (await db.collection("games").find(whereClause).count()) +
      (await db.collection("gifts").find(whereClause).count()) +
      (await db.collection("materials").find(whereClause).count()) )/ perPage
  );

  
  // return res.render('paginate', { bookings: results, pages: pages, perPage: perPage });

  return res.json({ results: results, pages: pages, currentPage: currentPage });
});
// Form for adding a single Book
router.put("/api/book/detail", async function (req, res) {
  let result = await db.collection("books").insertOne(req.body);

  return res.status(201).json({ id: result.insertedId });
});
// Form for adding a single Game
router.put("/api/game/detail", async function (req, res) {
  let result = await db.collection("games").insertOne(req.body);

  return res.status(201).json({ id: result.insertedId });
});

// Form for adding a single Gift
router.put("/api/gift/detail", async function (req, res) {
  let result = await db.collection("gifts").insertOne(req.body);

  return res.status(201).json({ id: result.insertedId });
});
// Form for adding a single Material
router.put("/api/material/detail", async function (req, res) {
  let result = await db.collection("materials").insertOne(req.body);

  return res.status(201).json({ id: result.insertedId });
});

// Form for updating a single Book
router.get("/api/book/detail/:id", async function (req, res) {
  console.log(req.params.id);
  if (!ObjectId.isValid(req.params.id))
    return res.status(404).send("Unable to find the requested resource!");

  let result = await db
    .collection("books")
    .findOne({ _id: ObjectId(req.params.id) });

  if (!result)
    return res.status(404).send("Unable to find the requested resource!");

  return res.json(result);
});

// Updating a single Book - Ajax
router.put("/api/book/detail/:id", async function (req, res) {
  if (!ObjectId.isValid(req.params.id))
    return res.status(404).send("Unable to find the requested resource!");

  var result = await db
    .collection("books")
    .findOneAndReplace({ _id: ObjectId(req.params.id) }, req.body);

  if (!result.value)
    return res.status(404).send("Unable to find the requested resource!");

  return res.send("Book updated.");
});

// Form for updating a single Game
router.get("/api/game/detail/:id", async function (req, res) {
  console.log(req.params.id);
  if (!ObjectId.isValid(req.params.id))
    return res.status(404).send("Unable to find the requested resource!");

  let result = await db
    .collection("games")
    .findOne({ _id: ObjectId(req.params.id) });

  if (!result)
    return res.status(404).send("Unable to find the requested resource!");

  return res.json(result);
});

// Updating a single game - Ajax
router.put("/api/game/detail/:id", async function (req, res) {
  if (!ObjectId.isValid(req.params.id))
    return res.status(404).send("Unable to find the requested resource!");

  var result = await db
    .collection("games")
    .findOneAndReplace({ _id: ObjectId(req.params.id) }, req.body);

  if (!result.value)
    return res.status(404).send("Unable to find the requested resource!");

  return res.send("Game updated.");
});

// Form for updating a single Gift
router.get("/api/gift/detail/:id", async function (req, res) {
  console.log(req.params.id);
  if (!ObjectId.isValid(req.params.id))
    return res.status(404).send("Unable to find the requested resource!");

  let result = await db
    .collection("gifts")
    .findOne({ _id: ObjectId(req.params.id) });

  if (!result)
    return res.status(404).send("Unable to find the requested resource!");

  return res.json(result);
});

// Updating a single gift - Ajax
router.put("/api/gift/detail/:id", async function (req, res) {
  if (!ObjectId.isValid(req.params.id))
    return res.status(404).send("Unable to find the requested resource!");

  var result = await db
    .collection("gifts")
    .findOneAndReplace({ _id: ObjectId(req.params.id) }, req.body);

  if (!result.value)
    return res.status(404).send("Unable to find the requested resource!");

  return res.send("Gift updated.");
});

// Form for updating a single Material
router.get("/api/material/detail/:id", async function (req, res) {
  console.log(req.params.id);
  if (!ObjectId.isValid(req.params.id))
    return res.status(404).send("Unable to find the requested resource!");

  let result = await db
    .collection("materials")
    .findOne({ _id: ObjectId(req.params.id) });

  if (!result)
    return res.status(404).send("Unable to find the requested resource!");

  return res.json(result);
});

// Updating a single material - Ajax
router.put("/api/material/detail/:id", async function (req, res) {
  if (!ObjectId.isValid(req.params.id))
    return res.status(404).send("Unable to find the requested resource!");

  var result = await db
    .collection("materials")
    .findOneAndReplace({ _id: ObjectId(req.params.id) }, req.body);

  if (!result.value)
    return res.status(404).send("Unable to find the requested resource!");

  return res.send("Material updated.");
});

// Delete a single Book
router.delete("/api/book/detail/:id", async function (req, res) {
  if (!ObjectId.isValid(req.params.id))
    return res.status(404).send("Unable to find the requested resource!");

  let result = await db
    .collection("books")
    .findOneAndDelete({ _id: ObjectId(req.params.id) });

  if (!result.value)
    return res.status(404).send("Unable to find the requested resource!");

  return res.status(204).send();
});

// Delete a single Game
router.delete("/api/game/detail/:id", async function (req, res) {
  if (!ObjectId.isValid(req.params.id))
    return res.status(404).send("Unable to find the requested resource!");

  let result = await db
    .collection("games")
    .findOneAndDelete({ _id: ObjectId(req.params.id) });

  if (!result.value)
    return res.status(404).send("Unable to find the requested resource!");

  return res.status(204).send();
});

// Delete a single Gift
router.delete("/api/gift/detail/:id", async function (req, res) {
  if (!ObjectId.isValid(req.params.id))
    return res.status(404).send("Unable to find the requested resource!");

  let result = await db
    .collection("gifts")
    .findOneAndDelete({ _id: ObjectId(req.params.id) });

  if (!result.value)
    return res.status(404).send("Unable to find the requested resource!");

  return res.status(204).send();
});

// Delete a single Material
router.delete("/api/material/detail/:id", async function (req, res) {
  if (!ObjectId.isValid(req.params.id))
    return res.status(404).send("Unable to find the requested resource!");

  let result = await db
    .collection("materials")
    .findOneAndDelete({ _id: ObjectId(req.params.id) });

  if (!result.value)
    return res.status(404).send("Unable to find the requested resource!");

  return res.status(204).send();
});

// GroupBy
router.get("/api/aggregate/groupby", async function (req, res) {
  let a = [
    { _id: "Book", count: await db.collection("books").count() },
    { _id: "Gift", count: await db.collection("gifts").count() },
    { _id: "Game", count: await db.collection("games").count() },
    { _id: "Material", count: await db.collection("materials").count() },
  ];
  return res.json(a);
});

//handle login
router.post("/api/login", async function (req, res) {

  if (req.body.password == "123456") {

    const user = {};

    const token = jwt.sign(
      { user_id: req.body.email }, "process.env.TOKEN_KEY", {
      expiresIn: "2h",
    }
    );
 
    user.token = token;

    return res.json(user);

  } else {
    res.status(401).send("Invalid Credentials");

  }

});


module.exports = router;
