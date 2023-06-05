'use stric'
const express = require('express');
const routes = require('./router/endpoin');
const app = express();

app.use(express.json());
app.use(routes);

app.get('/', (req, res) => {
  res.send('server berjalan'); 
})

// jalankan server
const host = 'localhost';
const port = process.env.PORT || 3000;

app.listen(port, host, () => {
  console.log(`server running at http://${host}:${port}`);
});
