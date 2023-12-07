/*
  Filename: User.js
  Student's Name: Rahul Gupta
  Student ID: 200553568
  Date: December 05, 2023
*/

const mongoose = require("mongoose");

const Schema = new mongoose.Schema({
  name: { type: String, default: null },
  email: { type: String, unique: true },
  password: { type: String },
  token: { type: String },
});

module.exports = mongoose.model("user", Schema);