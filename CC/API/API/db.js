const mysql = require('mysql');

const connection = mysql.createConnection({
  // TODO ubah, sesuaikan dengan database di cloud anda 
  host: '34.121.9.97',
  user: 'root',
  password: 'roott',
  database: 'capstone'
});

connection.connect((err) => {
  if (err) {
    throw err;
  }
  console.log('Connected to the MySQL server');
});

module.exports = connection;
