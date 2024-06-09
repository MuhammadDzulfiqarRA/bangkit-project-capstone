import json
import numpy as np
import random
import nltk
from nltk.stem import WordNetLemmatizer
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout
from tensorflow.keras.optimizers import SGD
from tensorflow.keras.models import load_model
from keras.callbacks import ModelCheckpoint
from tensorflow.keras.callbacks import EarlyStopping
import matplotlib.pyplot as plt

# Download NLTK data
nltk.download('punkt')
nltk.download('wordnet')

# Data intents JSON
data = {
    "intents": [
        {
            "tag": "mite",
            "patterns": ["Mite", "Cerita Mite", "Dongeng Mite"],
            "responses": [
                "Cerita dongeng jenis mite ialah dongeng yang bercerita tentang dewa-dewa, peri, dan segala sesuatu yang dianggap sederajat dengan dewa. Cerita-cerita ini mencerminkan keyakinan dan budaya suatu masyarakat, misal tentang datangnya padi ke Jawa."
            ]
        },
        {
            "tag": "legenda",
            "patterns": ["Legenda", "Cerita Legenda", "Dongeng Legenda"],
            "responses": [
                "Cerita legenda merupakan dongeng tentang terjadinya suatu tempat yang dihubungkan dengan kesaktian. Contoh dongeng jenis ini seperti terjadinya Gunung Tangkuban Perahu, cerita Malin Kundang, dan kisah Banyuwangi."
            ]
        },
        {
            "tag": "sage",
            "patterns": ["Sage", "Cerita Sage", "Dongeng Sage"],
            "responses": [
                "Sage ialah dongeng yang berhubungan dengan sejarah, maksudnya tokoh tokoh dalam sage seringkali menjadi tokoh dalam sejarah. Contoh dongeng sage adalah cerita berdirinya kerajaan Samodra dan awal mula Singosari."
            ]
        },
        {
            "tag": "fabel",
            "patterns": ["Fabel", "Cerita Fabel", "Dongeng Fabel"],
            "responses": [
                "Fabel adalah dongeng tentang binatang, tumbuhan-tumbuhan, dan benda-benda lain yang dapat berbicara dan berbuat seperti manusia. Contohnya cerita Si Kancil, Buaya dan Kera, serta Burung Gagak dan Burung Hantu."
            ]
        },
        {
            "tag": "jenaka",
            "patterns": ["Jenaka", "Cerita Jenaka", "Dongeng Jenaka"],
            "responses": [
                "Dongeng jenaka atau cerita pelipur lara ialah kisah jenaka atau menceritakan humor bangsa Indonesia. Contohnya seperti cerita Pak Pandir, Pak Kodok, dan Lebai Malang."
            ]
        },
        {
            "tag": "parabel",
            "patterns": ["Parabel", "Cerita Parabel", "Dongeng Parabel"],
            "responses": [
                "Dongeng parabel merupakan jenis dongeng yang dalam ceritanya tersirat nilai-nilai pendidikan, baik itu pendidikan agama, moral, atau pendidikan secara umum. Contohnya seperti Si Kerudung Merah, Hikayat Bayan Budiman, Malin Kundang, dan sebagainya."
            ]
        },
        {
            "tag": "dongeng_biasa",
            "patterns": ["Dongeng Biasa", "Cerita Biasa", "Dongeng Umum"],
            "responses": [
                "Selain dari jenis-jenis dongeng di atas, terdapat juga jenis dongeng biasa dan sangat umum diceritakan. Contohnya seperti Bawang Putih dan Bawang Merah, Cinderella, Putri Tidur, dan sebagainya."
            ]
        },
        {
            "tag": "greeting",
            "patterns": ["Hi", "Hai", "Hallo", "Halo", "Selamat pagi", "Selamat siang"],
            "responses": [
                "Apa kabar?",
                "Hai, apakah kamu membutuhkan saranku?",
                "Halo juga, senang bertemu denganmu!",
                "Hi, senang bertemu denganmu!"
            ]
        },
        {
            "tag": "response_to_greeting",
            "patterns": ["Baik", "Bagaimana denganmu", "Kabar", "Apa kabar?"],
            "responses": [
                "Saya baik, terima kasih telah bertanya!",
                "Saya sedang menjalankan tugas saya dengan baik!",
                "Luar biasa!"
            ]
        },
        {
            "tag": "thanks",
            "patterns": ["Terimakasih", "Makasih", "Thx"],
            "responses": [
                "Terimakasih Kembali!",
                "Bukan masalah!",
                "Dengan senang hati!"
            ]
        },
        {
            "tag": "greeting_response",
            "patterns": ["Apa kabar?", "Hai, apakah kamu membutuhkan saranku?", "Halo juga, senang bertemu denganmu!",
                         "Hi, senang bertemu denganmu!"],
            "responses": ["Aku baik, terima kasih telah bertanya!", "Sedang menjalankan tugas dengan baik!",
                          "Luar biasa!"]
        },
        {
            "tag": "thanks_response",
            "patterns": ["Terimakasih Kembali!", "Bukan masalah!", "Dengan senang hati!"],
            "responses": ["Sama-sama!", "Tidak masalah!", "Tidak ada masalah!"]
        },
        {
            "tag": "recommendation",
            "patterns": ["Minta saran cerita", "Beri saya saran cerita", "Rekomendasikan cerita",
                         "Ceritakan sebuah cerita", "Saran"],
            "responses": ["Saya akan mencari cerita terbaik untuk kamu..."],
            "stories": [
                "Atu Belah",
                "Pangeran Amat Mude",
                "Kisah Si Raja Parkit",
                "Terjadinya Danau Toba",
                "Putri Ular",
                "Dayang Bandir dan Sandean Raja",
                "Malin Kundang",
                "Si Lebai yang Malang",
                "Siamang Putih",
                "Legenda Ikan Patin",
                "Burung Tempua dan Burung Puyuh",
                "Putri Mambang Linau",
                "Si Kelingking",
                "Ibu Kami Seekor Kucing",
                "Si Pahit Lidah",
                "Legenda Pulau Kemaro",
                "Putri Kemarau",
                "Legenda Ular Berkepala Tujuh",
                "Legenda Ular N'Daung",
                "Asal-usul Batang Aren",
                "Buaya Perompak",
                "Si Bungsu",
                "Sultan Domas",
                "Bujang Katak",
                "Si Penyumpit",
                "Legenda Pulau Sanua",
                "Putri Pandan Berduri",
                "Ayam dan Ikan Tongkol",
                "Murtado Macan Kemayoran",
                "Si Pitung",
                "Monyet yang Malas",
                "Lutung Kasarung",
                "Sangkuriang & Dayang Sumbi",
                "Asal Mula Kota Cianjur",
                "Telaga Warna",
                "Legenda Gunung Tidar",
                "Timun Mas",
                "Cindelaras",
                "Asal-usul Nama Kali Gajah Wong",
                "Bawang Putih dan Bawang Merah",
                "Roro Jonggrang",
                "Keong Mas",
                "Calon Arang",
                "Kisah Lembu Sura",
                "Terjadinya Selat Bali",
                "Kebo Iwa",
                "Asal Mula Bukit Catu",
                "Sari Bulan",
                "Batu Golog",
                "Suri Ikun dan Dua Ekor Burung"
            ]
        },
        {
            "tag": "credit",
            "patterns": ["Credits", "Pembuat aplikasi", "Kredit", "Credit"],
            "responses": [
                "Credits Pembuatan Aplikasi ABI:\nTim Machine Learning:\n- Jason Christopher - Universitas Gunadarma - M009D4KY1942\n- Muslihah - Universitas Jenderal Achmad Yani Yogyakarta - M228D4KX2526\n- Roysihan - Universitas Esa Unggul - M204D4KY2005\nTim Mobile Development:\n- Muhammad Dzulfiqar Rizky Azhary - Universitas Mataram - A253D4KY3428\n- I Gede Restu Astikadeni - Universitas Mataram - A253D4KY4003\nTim Cloud Computing:\n- Ammar Ismail Khocan - Institut Informatika Dan Bisnis Darmajaya - C107D4KY0853\n- Ida Bagus Adi Kencana - Universitas Mataram - C253D4KY0667"]
        },
        {
            "tag": "help",
            "patterns": ["Help", "Bantuan", "Tolong", "Apa yang bisa kamu lakukan?"],
            "responses": [
                "Kamu dapat bertanya tentang hal-hal berikut:\n- Sapaan: Hi, Halo, Bye, Dadah\n- Ucapan : Terimakasih, Makasih\n- Rekomendasi: Rekomendasikan cerita, Beri saya saran cerita\n- ABI: Tentang aplikasi ABI, Info ABI, Pembuat aplikasi, Kredit"]
        },
        {
            "tag": "abi",
            "patterns": ["Tentang aplikasi ABI", "Info ABI", "Apa itu ABI?", "Aplikasi ABI"],
            "responses": [
                "Ayo Baca Indonesia (ABI) adalah aplikasi yang menghadirkan kisah-kisah menarik dari berbagai cerita rakyat di Indonesia.",
                "Dengan ABI, Kamu akan memasuki dunia petualangan yang memukau dari setiap sudut negeri, merasakan kekayaan budaya Indonesia.",
                "Di ABI, Kamu akan menemukan 50 cerita rakyat yang kaya akan makna dan pelajaran hidup, tersaji dalam format yang menarik dan interaktif.",
                "Kami hadirkan ABI untuk menjaga dan menghidupkan kembali warisan budaya bangsa, dan menyajikannya dengan cara yang menyenangkan bagi generasi muda."
            ]
        }
    ]
}
# BAGIAN 1
lemmatizer = WordNetLemmatizer()

words = []
classes = []
documents = []
ignore_words = ['?', '!']

# Loop melalui setiap intent dalam data
for intent in data['intents']:
    for pattern in intent['patterns']:
        word_list = nltk.word_tokenize(pattern)
        words.extend(word_list)
        documents.append((word_list, intent['tag']))
        if intent['tag'] not in classes:
            classes.append(intent['tag'])

words = [lemmatizer.lemmatize(w.lower()) for w in words if w not in ignore_words]
words = sorted(list(set(words)))
classes = sorted(list(set(classes)))

# Initialize training data
training_data = []

# Loop through each document
for doc in documents:
    # Initialize bag of words
    bag = []
    # Get the patterns and tag
    pattern_words, tag = doc
    # Lemmatize and normalize the pattern words
    pattern_words = [lemmatizer.lemmatize(word.lower()) for word in pattern_words]
    # Create bag of words
    for word in words:
        bag.append(1) if word in pattern_words else bag.append(0)

    # Initialize output row
    output_row = [0] * len(classes)
    output_row[classes.index(tag)] = 1

    # Concatenate bag of words and output row into a single list
    training_data.append([bag + output_row])

# Shuffle training data
random.shuffle(training_data)

# Print shape of each item in training data for debugging
print("Shape of training data before conversion:", len(training_data))
for item in training_data:
    print("Shape of item:", len(item))

# Convert training data to NumPy array
training_data = np.array(training_data)

# Reshape training data to remove extra dimension
training_data = training_data.reshape(training_data.shape[0], -1)

# Split input and output
train_x = training_data[:, :-len(classes)]
train_y = training_data[:, -len(classes):]

print("Shape of training data:", training_data.shape)
# BAGIAN 2: Melatih Model

from keras.callbacks import ModelCheckpoint
import matplotlib.pyplot as plt

# Inisialisasi model Sequential
model = Sequential()
# Tambahkan layer input dengan jumlah neuron sesuai dengan jumlah fitur input (75)
model.add(Dense(128, input_shape=(len(train_x[0]),), activation='relu'))
# Tambahkan layer dropout untuk mengurangi overfitting
model.add(Dropout(0.5))
# Tambahkan layer output dengan jumlah neuron sesuai dengan jumlah kelas (jumlah intent)
model.add(Dense(len(train_y[0]), activation='softmax'))

# Compile model with the SGD optimizer
sgd = SGD(learning_rate=0.01, momentum=0.9, nesterov=True)
model.compile(loss='categorical_crossentropy', optimizer=sgd, metrics=['accuracy'])

# Definisikan early stopping untuk mencegah overfitting dan menghentikan pelatihan jika tidak ada peningkatan dalam validasi
early_stopping = EarlyStopping(monitor='val_loss', patience=5, verbose=1, mode='auto')

# Callback untuk menyimpan model dengan performa terbaik berdasarkan val_accuracy
checkpoint = ModelCheckpoint('sexy_model.h5', monitor='val_accuracy', verbose=1, save_best_only=True, mode='max')

# Latih model dengan data latih dan validasi
history = model.fit(train_x, train_y, epochs=100, batch_size=5, verbose=1, validation_split=0.1, callbacks=[early_stopping, checkpoint])

# Plot loss dan akurasi selama pelatihan
plt.plot(history.history['loss'], label='Training Loss')
plt.plot(history.history['val_loss'], label='Validation Loss')
plt.plot(history.history['accuracy'], label='Training Accuracy')
plt.plot(history.history['val_accuracy'], label='Validation Accuracy')
plt.title('Training and Validation Metrics')
plt.xlabel('Epochs')
plt.ylabel('Metrics')
plt.legend()
plt.show()

# Simpan model setelah pelatihan selesai
# model.save('chatbot_model.h5')

# BAGIAN 3

def clean_up_sentence(sentence):
    sentence_words = nltk.word_tokenize(sentence)
    sentence_words = [lemmatizer.lemmatize(word.lower()) for word in sentence_words]
    return sentence_words

def bow(sentence, words, show_details=True):
    sentence_words = clean_up_sentence(sentence)
    bag = [0] * len(words)
    for s in sentence_words:
        for i, w in enumerate(words):
            if w == s:
                bag[i] = 1
                if show_details:
                    print(f"Found in bag: {w}")
    return np.array(bag)

def predict_class(sentence, model):
    p = bow(sentence, words, show_details=False)
    res = model.predict(np.array([p]))[0]
    ERROR_THRESHOLD = 0.3
    results = [[i, r] for i, r in enumerate(res) if r > ERROR_THRESHOLD]
    results.sort(key=lambda x: x[1], reverse=True)
    return_list = []
    for r in results:
        return_list.append({"intent": classes[r[0]], "probability": str(r[1])})
    if not return_list:
        return_list.append({"intent": "no_match", "probability": "0.0"})
    return return_list

def get_story_recommendation(intents):
    for intent in intents['intents']:
        if intent['tag'] == 'recommendation':
            if 'stories' in intent:
                stories = intent['stories']
                recommended_story = random.choice(stories)
                return recommended_story
    return "Maaf, tidak ada cerita yang tersedia saat ini."

# Gunakan fungsi get_story_recommendation() di dalam fungsi get_response()
def get_response(ints, intents_json):
    if not ints:
        return "Maaf, saya tidak mengerti. Jika kamu membutuhkan bantuan, kamu bisa menuliskan 'help' atau 'tolong'."

    tag = ints[0]['intent']
    list_of_intents = intents_json['intents']

    for intent in list_of_intents:
        if intent['tag'] == tag:
            if tag == 'recommendation':
                recommended_story = get_story_recommendation(intents_json)
                result = f"Menurutku, cerita yang menarik untuk kamu adalah {recommended_story}."
            elif tag == 'response_to_greeting':
                result = random.choice(intent['responses'])
            else:
                result = random.choice(intent['responses'])
            break
    else:
        result = "Maaf, saya tidak mengerti. Jika kamu membutuhkan bantuan, kamu bisa menuliskan 'help' atau 'tolong'."

    return result

def chatbot_response(msg):
    ints = predict_class(msg, model)
    res = get_response(ints, data)
    return res

# FINAL STEP TESTING
while True:
    message = input("Kamu: ")
    if message.lower() == "finish":
        break
    response = chatbot_response(message)
    print(f"Bot: {response}")