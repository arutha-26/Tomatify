const express = require('express');
const router = express.Router();

const userController = require('../controller/user');

router.post('/register', userController.register);
router.get('/verify', userController.verify);
router.post('/login', userController.login);

module.exports = router;
