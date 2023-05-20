const express = require('express');
const router = express.Router();

const userController = require('../controller/user');
const uploadImg = require('../controller/upImg');

router.post('/register', userController.register);
router.get('/verify', userController.verify);
router.post('/login', userController.login);
router.use(uploadImg);

module.exports = router;
