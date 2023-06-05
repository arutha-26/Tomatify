from google.cloud import storage
import tensorflow as tf
from PIL import Image
import numpy as np

BUCKET_NAME = ...
class_name = ["Bacterial Spot", "Early Blight", "Late Blight", "Leaf Mold", "Mosaic Virus", "Septoria Leaf Spot", "Spider Mites", "Target Spot", "Yellow Leaf Curl"]

model = None

def download_blob(bucket_name, source_blod_name, destination_file_name):
    storage_client = storage.Client()
    bucket = storage_client.get_bucket(bucket_name)
    blob = bucket.blob(source_blod_name)
    blob.download_to_filename(destination_file_name)

def predict(request):
    global model
    if model is not None:
        download_blob(
            BUCKET_NAME,
            "directory model", #isi directory model yang sudah diconvert h5, dan file h5 sudah ada di bucket
            "directory model2", #isi directory untuk dowload local?
        )
        model = tf.keras.models.load_model("directory model2")

    image = request.files["file"] #variabel yang menampung gambar yang diupload oleh user utk diprediksi

    #convert image diupload oleh user utk diprediksi ke RGB dan ukur 256 x 256, sesuai dengan ukuran yang ditraining model
    image = np.array(Image.open(image).convert("RGB").resize((256,256)))
    #normalisasi image jadi range 0 sampai 1
    image = image/255
    img_array = tf.expand_dims(image, 0)

    predictions = model.predict(img_array)

    #untuk menunjukan hasil prediksi
    predicted_class = class_name[np.argmax(predictions[0])]

    return{"class" :predicted_class}
