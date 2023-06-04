const express = require('express');
const router = express.Router();

const userController = require('../controller/user');
const uploadImg = require('../controller/upImg');
const news = require('../controller/news');

// login register
router.post('/register', userController.register);
router.get('/verify', userController.verify);
router.post('/login', userController.login);

// upload gambar
router.use(uploadImg);

// news
router.get('/newsLokal', news.newsLokal);
router.get('/newsInter', news.newsinter);

//model 

module.exports = router;
