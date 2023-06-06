const crypto = require('crypto');
const connection = require('../db');
const transporter = require('../mailer');
const fs = require('fs');
const path = require('path');

module.exports = {
      // logic verify
  register: (req, res) => {
    const { name, email, password } = req.body;
    const verificationToken = crypto.randomBytes(20).toString('hex');
  
    // Check if email already exists in the database
    connection.query('SELECT * FROM users WHERE email = ?', email, (err, result) => {
      if (err) {
        console.error(err);
        return res.status(500).json({ message: 'Failed to register user' });
      }
  
      // If email already exists, return an error response
      if (result.length > 0) {
        return res.status(400).json({ message: 'Email already exists' });
      }
  
      const newUser = {
        name,
        email,
        password,
        verification_token: verificationToken,
        is_verified: false
      };
  
      connection.query('INSERT INTO users SET ?', newUser, (err, result) => {
        if (err) {
          console.error(err);
          return res.status(500).json({ message: 'Failed to register user' });
        }
  
        const verificationLink = `http://localhost:3000/verify?token=${verificationToken}`;
        
        const verificationHtmlPath = path.join(__dirname, '/html/email.html');
        let verificationHtmlContent;
  
        try {
          verificationHtmlContent = fs.readFileSync(verificationHtmlPath, 'utf-8');
        } catch (error) {
          console.error(error);
          return res.status(500).json({ message: 'Failed to load verification template' });
        }
  
        const emailContent = verificationHtmlContent.replace('{verificationLink}', verificationLink);
  
        transporter.sendMail({
          to: email,
          subject: 'Account Verification',
          html: emailContent
        }, (error) => {
          if (error) {
            console.error(error);
            return res.status(500).json({ message: 'Failed to send verification email' });
          }
          res.status(200).json({ message: 'User registered successfully. Please check your email for verification.' });
        });
      });
    });
  },

      // logic verify
  verify: (req, res) => {
        const { token } = req.query;
      
        connection.query('UPDATE users SET is_verified = true WHERE verification_token = ?', token, (err, result) => {
          if (err) {
            console.error(err);
            return res.status(500).json({ message: 'Failed to verify account' });
          }
      
          const htmlPath = path.join(__dirname, '/html/verification.html');
          const htmlContent = fs.readFileSync(htmlPath, 'utf-8');
      
          res.status(200).send(htmlContent);
        });
  },

      // logic login 
  login : (req, res) => {
        const { email, password } = req.body;
      
        connection.query('SELECT * FROM users WHERE email = ?', email, (err, result) => {
          if (err) {
            console.error(err);
            return res.status(500).json({ message: 'Failed to fetch user' });
          }
      
          const user = result[0];
          if (!user) {
            return res.status(401).json({ message: 'Invalid email or password' });
          }
      
          if (!user.is_verified) {
            return res.status(401).json({ message: 'Account is not verified' });
          }
      
          // Bandingkan password dengan user.password
          connection.query('SELECT * FROM users WHERE email = ?', email, (err, result) => {
            if (err) {
              console.error(err);
              return res.status(500).json({ message: 'Failed to fetch user' });
            }
          
            const user = result[0];
            if (!user) {
              return res.status(401).json({ message: 'Invalid email or password' });
            }
      
            // Lakukan verifikasi password secara langsung
            if (user.password !== password) {
              return res.status(401).json({ message: 'Invalid email or password' });
            }
          
            res.status(200).json({ message: 'Logged in successfully' });
          });
        });
  }
}
