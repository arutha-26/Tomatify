from google.cloud import storage
import tensorflow as tf
from PIL import Image
import numpy as np

model = None
class_names = ['bacterial_spot', 'early_blight', 'healthy', 'late_blight', 'leaf_mold', 'mosaic_virus', 'septoria_leaf_spot', 'spider_mites', 'target_spot', 'yellow_leaf_curl']
class_description = ['Penyakit ini disebabkan oleh empat spesies Xanthomonas. Gejala bacterial spot dimulai sebagai lesi kecil berwarna kuning kehijauan pada daun muda atau lesi gelap, basah kuyup, tampak berminyak pada daun yang lebih tua yang berubah menjadi cokelat hingga merah kecoklatan.',
                    'Early Blight adalah penyakit yang dapat menyebar yang mempengaruhi tomat, disebabkan oleh patogen jamur Alternaria linariae (syn. A. tomatophila), gejalanya meliputi lesi pada batang tomat, buah, dan dedaunan, yang menyebabkan jumlah panen tomat menurun',
                    'Tanaman tomat yang sehat ditandai dengan daun yang lembut dengan warna hijau sedang hingga gelap dengan batang yang kokoh',
                    'Penyakit late blight atau penyakit busuk daun adalah salah satu penyakit yang paling merusak tanaman tomat yang disebabkan oleh  Phytophthora infestans (Mont.)',
                    'Penyakit leaf mold atau penyakit jamur daun tomat adalah penyakit yang umum terjadi pada tanaman tomat. Penyakit ini biasanya terjadi pada lingkungan bersuhu tinggi dan lembab (kondisi yang kondusif untuk penyebaran penyakit dan pertumbuhan pathogen yang cepat). Penyakit jamur daun tomat ini disebabkan oleh pathogen Cladosporium fulvum (C. fulvum)',
                    'Mosaic virus adalah virus tanaman yang gampang menular. Virus ini menyerang tanaman toman yang berada di dalam ruangan(rumah kaca) dan luar ruangan. Mosaic virus menyebabkan belang hijau muda dan tua, tanaman kerdil dan kadang-kadang distorsi daun',
                    'Septoria Leaf Spot disebabkan oleh jamur Septoria lycopersici. Penyakit ini ditandai dengan munculnya bintik-bintik kecil yang basah yang akan menjadi bintik-bintik melingkar dengan diameter sekitar 1/8 inci',
                    'Spider Mites adalah penyakit yang disebabkan oleh hama arakhnida invasif, yang ditandai dengan daun menjadi putih kekuningan dan berbintik-bintik. Spider mites dapat ditemukan di kedua sisi daun tetapi lebih sering ada di bagian bawah dekat urat daun',
                    'Gejala yang ditimbulkan oleh penyakit ini dimulai dengan munculnya lesi gelap kecil yang membesar membentuk lesi coklat muda dengan pola konsentris',
                    'Gejala khas yang tanaman yang terinfeksi penyakit dengan infeksi Yellow leaf curl adalah tanaman menjadi kerdil, klorosis daun melengkung ke atas, pengurangan ukuran daun dan penurunan produksi tomat. Penyakit ini ditularkan antar tanaman oleh serangga, Besimia tabaci (umumnya dikenal sebagai kutu kebul)']
class_prevent =['Melakukan penanaman dengan bibit yang bebas penyakit sangat penting untuk mengendalikan penyakit ini dan penyakit bakteri lainnya, karena bakteri dapat ditularkan ke bibit dari benih yang terkontaminasi. Dianjurkan untuk menghindari penanganan tanaman (pemangkasan dan pengikatan, misalnya) lebih dari yang diperlukan, karena luka akibat penanganan memungkinkan bakteri masuk ke dalam tanaman. Semprotan produk tembaga dapat mengurangi penyebaran penyakit jika disemprotkan saat gejala pertama kali muncul',
                'Untuk mengatasi dan mencegah munculnya penyakit ini, gunakan benih bebas patogen, atau kumpulkan benih hanya dari tanaman bebas penyakit, mengendalikan gulma yang rentan seperti nightshade hitam dan nightshade berbulu. Selain itu lakukan pemupukan dengan benar untuk mempertahankan pertumbuhan tanaman yang kuat, jangan memupuk secara berlebihan dengan potasium dan pertahankan tingkat nitrogen dan fosfor yang memadai. Gunakan mulsa plastik atau organik untuk memberikan penghalang antara tanah dan daun yang terkontaminasi',
                'Tanaman tomat yang sehat membutuhkan makronutrien seperti nitrogen, fosfor, dan kalium, dan mikronutrien seperti magnesium, kalsium, dan seng',
                'Pengendalian penyakit busuk daun memerlukan budidaya atau praktik pertanian yang baik, aplikasi fungisida, dan penggunaan kultivatur yang tahan. Tahapan yang dapat dilakukan untuk budidaya tanaman tomat untuk penanggulangan penyakit busuk daun adalah dengan menerapkan rotasi tanaman dan masa bera (masa istirahat), mengeliminasi tanaman tomat yang sudah terinfeksi penyakit busuk daun, dan menjauhkan produk tomat yang membusuk. Campuran fungisida yang dirancang untuk memperlambat perkembangan penyakit salah satunya adalah Fungisida Metalaxyl. Namun, perawatan ini menjadi tidak efektif ketika perkembangan penyakit sedang kondusif',
                'Tahapan yang dapat dilakukan untuk budidaya tanaman tomat untuk penanggulangan penyakit leaf mold adalah dengan memotong daun yang telah terinfeksi dan daun yang terinfeksi dibuang jauh dari area tanaman tomat. Gunakan fungisida organic yang dapat membantu mengendalikan populasi jamur tanpa merusak tanaman lain seperti ekstrak bawang putih. Selain itu, mengaplikasikan pupuk dengan dosis sesuai aturan',
                'Untuk pencegahan virus ini, gunakan bibit yang berasal dari tanaman yang sehat yang tidak pernah terjangkit virus. Jika sudah terjangkit mosaic virus, lakukan isolasi dengan kantong plastik yang diisi dengan pupuk kompos. Isolasi juga dapat dilakukan dengan cara membuat jarak dengan tanaman tomat yang terinfeksi sehingga meminimalisir penularan virus. Gunakan produk pengendalian tanaman alami yang tidak beracun seperti Safer Soap, Bon-Neem, and diatomaceous earth, untuk mengurangi jumlah serangga yang membawa penyakit',
                'Penyakit ini dapat dicegah dengan menanam tanaman tomat dengan jarak yang cukup sehingga daunnya cepat kering, kemudian siram bagian pangkal tanaman tomat saat pagi untuk mengurangi jumlah daun yang basah. Hindari merawat tanaman tomat saat daunnya sedang basah untuk menghindari penyebaran mikroorganisme penyebab penyakit. Fungisida yang efektif untuk menanggulangi penyakit ini adalah fungisida yang mengandung beberapa bahan aktif seperti berikut, chlorothalonil, penthiopyrad, difenoconazole, cyprodinil dan mancozeb',
                'Untuk mencegah dan mengatasi penyakit ini, hilangkan tanaman yang merupakan inang umum tungau laba-laba, misalnya nightshade liar dan bayam, hindari tanaman yang kekurangan air karena dapat mendorong wabah spider mites. Gunakan minyak pestisida (minyak putih berdasarkan minyak nabati), minyak hortikultura atau sabun(sabun murni, bukan detergen). Untuk produk minyak hortikultura, ikuti petunjuk pada label produk. Pastikan minyak dapat digunakan pada tanaman yang diminati, jika tidak maka dapat merusak dedaunan. Semprotkan bagian bawah daun, karena minyak harus mengenai tungau. Selain itu, gunakan produk yang mengandung belerang - produk yang diizinkan berdasarkan sertifikasi organic dan abamektin, produk yang berasal dari bakteri tanah',
                'Untuk mencegah dan mengatasi penyakit ini, pangkas beberapa cabang dari bagian bawah tanaman untuk memungkinkan aliran udara yang lebih baik di pangkalan kemudian buang dan bakar daun bagian bawah segera setelah penyakit terlihat, terutama setelah batang buah bagian bawah dipetik. Kondisi basah yang hangat mendukung penyakit sehingga fungisida diperlukan untuk memberikan kontrol yang memadai, gunakan produk seperti chlorothalonil, copper oxychloride atau mancozeb. Perawatan harus dimulai saat bercak pertama terlihat dan dilanjutkan dengan interval 10-14 hari hingga 3-4 minggu sebelum panen terakhir. Penting untuk menyemprot kedua sisi daun',
                'Penyakit ini dapat dikendalikan dengan pengendalian kimia. Setelah terinfeksi virus, tidak ada pengobatan terhadap infeksi sehinggan untuk mengontrol populasi serangga untuk menghindari infeksi virus digunakan insektisida dari keluarga piretroid yang dapat digunakan sebagai pembasmi tanah atau semprotan selama tahap pembibitan dapat mengurangi populasi serangga']

BUCKET_NAME = "model-tomatify" # Ganti dengan nama bucket GCP Anda

def download_blob(bucket_name, source_blob_name, destination_file_name):
    """Downloads a blob from the bucket."""
    storage_client = storage.Client()
    bucket = storage_client.get_bucket(bucket_name)
    blob = bucket.blob(source_blob_name)

    blob.download_to_filename(destination_file_name)

    print(f"Blob {source_blob_name} downloaded to {destination_file_name}.")

def load_model():
    global model
    if model is None:
        download_blob(
            BUCKET_NAME,
            "models/model.h5",
            "/tmp/model.h5",
        )
        model = tf.keras.models.load_model("/tmp/model.h5")

def predict(request):
    load_model()

    image = request.files["file"]

    image = np.array(
        Image.open(image).convert("RGB").resize((256, 256)) # image resizing
    )

    image = image / 255.0 # normalize the image in the range of 0 to 1

    img_array = tf.expand_dims(image, 0)
    predictions = model.predict(img_array)

    print("Predictions:", predictions)

    predicted_class = class_names[np.argmax(predictions[0])]
    confidence = round(100 * np.max(predictions[0]), 2)
    disease_description = class_description[np.argmax(predictions[0])]
    disease_prevention = class_prevent[np.argmax(predictions[0])]

    return {"class": predicted_class, "confidence": confidence, "description" : disease_description, "prevention" : disease_prevention}
