const express = require('express');
const router = express.Router();
const controller = require('../controller/user');

router.get('/users', controller.read);
router.post('/users', controller.create); 
router.put('/users/:usersid', controller.update);
router.delete('/users/:usersId', controller.delete);

module.exports = router;
