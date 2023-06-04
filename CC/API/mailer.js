const nodemailer = require('nodemailer');

const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'tomatifycapstone@gmail.com',
    pass: 'hbhpvaalunxvmefx'
  }
});

module.exports = transporter;
