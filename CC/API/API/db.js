const mysql = require('mysql');

const connection = mysql.createConnection({
  // TODO ubah, sesuaikan dengan database di cloud anda 
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'capatone'
});

connection.connect((err) => {
  if (err) {
    throw err;
  }
  console.log('Connected to the MySQL server');
});

module.exports = connection;
