const express = require('express');
const { Storage } = require('@google-cloud/storage');
const Multer = require('multer');

// endpoint untuk mengupload gambar
app.post('/upload', multer.single('image'), async (req, res, next) => {
  try {
    // ambil file gambar dari request
    const file = req.file;

    // simpan file gambar ke Google Cloud Storage
    const bucketName = 'latihan_upload_gambar';
    const filename = `${Date.now()}_${file.originalname}`;
    const bucket = storage.bucket(bucketName);
    const blob = bucket.file(filename);
    const blobStream = blob.createWriteStream();

    blobStream.on('error', (err) => next(err));

    blobStream.on('finish', () => {
      // set public access ke file gambar agar bisa diakses dari publik
      blob.makePublic().then(() => {
        const publicUrl = `https://storage.googleapis.com/${bucketName}/${filename}`;
        res.json({ url: publicUrl });
      });
    });

    blobStream.end(file.buffer);
  } catch (err) {
    next(err);
  }
});

// jalankan server
const host = 'localhost';
const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`Server berjalan pada port http://${host}:${port}`);
});
