/*
  Filename: Book.js
  Student's Name: Rahul Gupta
  Student ID: 200553568
  Date: December 05, 2023
*/

const mongoose = require("mongoose");

const Schema = new mongoose.Schema({
  name: { type: String, required: true },
  isbn: { type: String, unique: true, required: true },
  rating: { type: Number, default: 0 },
  author: { type: String, default: null },
  publisher: { type: String, default: null },
});

module.exports = mongoose.model("book", Schema);