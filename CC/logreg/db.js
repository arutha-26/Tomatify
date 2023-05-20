const mysql = require('mysql');

const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'capstone'
});

connection.connect((err) => {
  if (err) {
    throw err;
  }
  console.log('Connected to the MySQL server');
});

module.exports = connection;
