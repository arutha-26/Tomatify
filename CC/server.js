const express = require('express');
const userRouter = require('./ruter/user.js');
const router = express.Router(); 

const app = express(); 

app.use(express.json()); 
app.use(express.urlencoded({ extended: true })); 

function myLoger(request, response, next) {
    // console.log('LOGED');
    request.time = new Date(); 
    next();
};

app.use(myLoger);

app.set('view engine', 'ejs')

app.get('/', (request, response) => {
    // response.send('response sucess');
    const kelas = {
        id : 123, 
        nama : 'bharata',
        Date : request.time.toString()
    }
    response.render('./pages/index.ejs', {kelas : kelas});
});

app.get('/about', (request, response) => {
    const user = {
        id : 123, 
        nama : 'nyoman',
        kelas : 12, 
        alamat : 'jember'
    }
    response.render('./pages/about.ejs', {user : user}); 
});

const host = 'localhost';
app.use(userRouter);

const PORT = process.env.PORT || 8000
app.listen(PORT, () => {
    console.log(`Server berjalan di:  http://${host}:${PORT}`);
}); 


