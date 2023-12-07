/*
  Filename: index.js
  Student's Name: Charvi Parhawk
  Student ID: 200557117
  Date: December 05, 2023
*/

const http = require("http");
const routes = require("./routes/routes");
const server = http.createServer(routes);

const { API_PORT } = process.env;
const port = process.env.PORT || API_PORT;

// server listening 
server.listen(port, () => {
  console.log(`Server running on port ${port}`);
});