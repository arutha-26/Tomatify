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

BUCKET_NAME = "model-tomat" # Ganti dengan nama bucket GCP Anda

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

    return {"class": predicted_class, "confidence": confidence}
