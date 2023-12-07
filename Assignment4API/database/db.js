/*
  Filename: db.js
  Student's Name: Charvi Parhawk
  Student ID: 200557117
  Date: December 05, 2023
*/

const mongoose = require("mongoose");

const { MONGO_URI } = process.env;

exports.connect = () => {
  // Connecting to the database
  mongoose
    .connect(MONGO_URI, {
      useNewUrlParser: true,
      useUnifiedTopology: true

    })
    .then(() => {
      console.log("Successfully connected to database");
    })
    .catch((error) => {
      console.log("Connection failed!");
      console.error(error);
     
    });
};