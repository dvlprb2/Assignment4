/*
  Filename: routes.js
  Student's Name: Charvi Parhawk & Rahul Gupta
  Student ID: 200557117 & 200553568
  Date: December 05, 2023
*/

require("dotenv").config();
require("../database/db").connect();
const express = require("express");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const auth = require("../middleware/auth");

const User = require("../models/User");
const Book = require("../models/Book");
const app = express();

app.use(express.json());

app.get("/", (req, res) => {
  res.status(200).send("Welcome to Group-6 Assignment-4");
});

// Endpoint to register user
app.post("/register", async (req, res) => {
  try {
    // Get user credentials
    const { name, email, password } = req.body;

    // Validate user input
    if (!(email && password && name)) {
      res.status(400).send("All fields are mandatory");
    }

    const CheckUser = await User.findOne({ email });

    if (CheckUser) {
      return res.status(409).send("User is already registered");
    }

    //Encrypt user password
    encryptedPassword = await bcrypt.hash(password, 10);

    // Create user in our database
    const user = await User.create({
      name,
      email: email.toLowerCase(),
      password: encryptedPassword,
    });

    // Create token
    const token = jwt.sign(
      { user_id: user._id, email },
      process.env.TOKEN_KEY,
      {
        expiresIn: "2h",
      }
    );

    user.token = token;

    res.status(201).json(user);
  } catch (err) {
    console.log(err);
  }

});

// Endpoint to login
app.post("/login", async (req, res) => {

  // Our login logic starts here
  try {
    // Get user input
    const { email, password } = req.body;

    // Validate user input
    if (!(email && password)) {
      res.status(400).send("All input is required");
    }
    // Validate if user exist in our database
    const user = await User.findOne({ email });

    if (user && (await bcrypt.compare(password, user.password))) {
      // Create token
      const token = jwt.sign(
        { user_id: user._id, email },
        process.env.TOKEN_KEY,
        {
          expiresIn: "15m",
        }
      );

      // save user token
      user.token = token;

      // user
      res.status(200).json(user);
    }

  } catch (err) {
    console.log(err);
  }

});

// Endpoint to list all books
app.get("/books", auth, async (req, res) => {
  try {
    const books = await Book.find();
    res.json(books);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Endpoint to find a book by ID
app.get("/books/:id", auth, async (req, res) => {
  const { id } = req.params;
  try {
    const book = await Book.findById(id);
    res.json(book);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Endpoint to add a new book
app.post("/books", auth, async (req, res) => {
  const bookData = req.body;
  try {
    const newBook = await Book.create(bookData);
    res.status(201).json(newBook);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Endpoint to update an existing book
app.put('/books/:id', auth, async (req, res) => {
  const { id } = req.params;
  const updatedBookData = req.body;
  try {
    const updatedBook = await Book.findByIdAndUpdate(id, updatedBookData, { new: true });
    res.json(updatedBook);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Endpoint to delete an existing book
app.delete('/books/:id', auth, async (req, res) => {
  const { id } = req.params;
  try {
    await Book.findByIdAndDelete(id);
    res.json({ message: 'Book deleted successfully' });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = app;