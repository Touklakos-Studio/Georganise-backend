from itertools import count
import hashlib
import csv
import math
import random
import uuid
import base64
import os

succes = " created successfully"
path = os.path.dirname(os.path.abspath(__file__))
image_path = [f"{path}\\Ressources\\{f}" for f in os.listdir(path + "\\Ressources\\") if os.path.isfile(os.path.join(path + "\\Ressources\\", f))]

class users:
    ids = count(1)
    def __init__(self):
        self.id = next(self.ids)
        self.generate_data()
        print("User" + succes, self)

    def __str__(self):
        return f"USER({self.id}, {self.nickname}, {self.email}, {self.password})"
    
    def generate_data(self):
        self.nickname = f"User{self.id}"
        self.email = f"{self.nickname}@email.com"
        self.password = users.hash_password(f"Password{self.id}")
        self.token = None

    @staticmethod
    def hash_password(password):
        return hashlib.sha256(password.encode()).hexdigest()
    
    @staticmethod
    def save_users(users):
        with open(path + '\\users.csv', 'w', newline='') as csvfile:
            fieldnames = ['ID', 'NICKNAME', 'EMAIL', 'PASSWORD', 'TOKEN']
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
            writer.writeheader()
            for user in users:
                writer.writerow({'ID': user.id, 'NICKNAME': user.nickname, 'EMAIL': user.email, 'PASSWORD': user.password, 'TOKEN': user.token})

class images:
    ids = count(1)
    def __init__(self, user):
        self.id = next(self.ids)
        self.user = user
        self.generate_data()
        print("Image" + succes, self)

    def __str__(self):
        return f"IMAGE({self.id}, {self.user.id}, {self.image[:10]}, {self.name}, {self.description})"
    
    def generate_data(self):
        with open(random.choice(image_path), "rb") as f:
            self.image = base64.b64encode(f.read()).decode('utf-8')
        self.name = f"Image{self.id}"
        self.description = f"Description{self.id}"
        self.public = random.choice([True, False])

    @staticmethod
    def save_images(images):
        with open(path + '\\images.csv', 'w', newline='') as csvfile:
            fieldnames = ['ID', 'IMAGE', 'NAME', 'DESCRIPTION', 'USERID', 'PUBLIC']
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
            writer.writeheader()
            for image in images:
                writer.writerow({'ID': image.id, 'IMAGE': image.image, 'NAME': image.name, 'DESCRIPTION': image.description, 'USERID': image.user.id, 'PUBLIC': image.public})

class places:
    ids = count(1)
    def __init__(self, user, image):
        self.id = next(self.ids)
        self.user = user
        self.image = image
        self.generate_data()
        print("Place" + succes, self)

    def __str__(self):
        return f"PLACE({self.id}, {self.name}, {self.lat}, {self.lng}, {self.description}, {self.image}, {self.user})"

    def generate_data(self):
        self.name = f"Place{self.id}"
        self.lat, self.lng = places.randlatlon()
        self.description = f"Description{self.id}"

    @staticmethod
    def randlatlon():
        pi = math.pi
        cf = 180.0 / pi  # radians to degrees Correction Factor

        # get a random Gaussian 3D vector:
        gx = random.gauss(0.0, 1.0)
        gy = random.gauss(0.0, 1.0)
        gz = random.gauss(0.0, 1.0)

        # normalize to an equidistributed (x,y,z) point on the unit sphere:
        norm2 = gx*gx + gy*gy + gz*gz
        norm1 = 1.0 / math.sqrt(norm2)
        x = gx * norm1
        y = gy * norm1
        z = gz * norm1

        radlat = math.asin(z)      # latitude  in radians
        radlon = math.atan2(y,x)   # longitude in radians
        
        return (round(cf*radlat, 5), round(cf*radlon, 5))
    
    @staticmethod
    def save_places(places):
        with open(path + '\\places.csv', 'w', newline='') as csvfile:
            fieldnames = ['ID', 'NAME', 'LAT', 'LNG', 'DESCRIPTION', 'IMAGE', 'USER']
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
            writer.writeheader()
            for place in places:
                writer.writerow({'ID': place.id, 'NAME': place.name, 'LAT': place.lat, 'LNG': place.lng, 'DESCRIPTION': place.description, 'IMAGE': None if place.image == None else place.image.id, 'USER': place.user.id})

class tags:
    ids = count(1)
    def __init__(self, user, realtime = False):
        self.id = next(self.ids)
        self.user = user
        self.generate_data(realtime)
        print("Tag" + succes, self)

    def __str__(self):
        return f"TAG({self.id}, {self.user.id}, {self.title}, {self.description})"
    
    def generate_data(self, realtime):
        self.title = f"Tag{self.id}" if not realtime else "{" + self.user.nickname + "} real time"
        self.description = f"Description{self.id}" if not realtime else "Real time positions of user: " + self.user.nickname

    @staticmethod
    def save_tags(tags):
        with open(path + '\\tags.csv', 'w', newline='') as csvfile:
            fieldnames = ['ID', 'TITLE', 'DESCRIPTION', 'USERID']
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
            writer.writeheader()
            for tag in tags:
                writer.writerow({'ID': tag.id, 'TITLE': tag.title, 'DESCRIPTION': tag.description, 'USERID': tag.user.id})

class places_tags:
    ids = count(1)
    def __init__(self, place):
        self.id = next(self.ids)
        self.place = place
        self.tag = []
        print("Place_Tag" + succes, self)

    def __str__(self):
        return f"PLACE_TAG({self.id}, {self.place}, {self.tag})"
    
    def add_tag(self, tag):
        if tag in self.tag:
            print("Tag already in place_tag")
            return False
        self.tag.append(tag)
        return True
    
    @staticmethod
    def save_places_tags(places_tags):
        with open(path + '\\places_tags.csv', 'w', newline='') as csvfile:
            fieldnames = ['ID', 'PLACE', 'TAG']
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
            writer.writeheader()
            for place_tag in places_tags:
                for tag in place_tag.tag:
                    writer.writerow({'ID': place_tag.id, 'PLACE': place_tag.place.id, 'TAG': tag.id})

class tokens:
    ids = count(1)
    def __init__(self, tag, user = None):
        self.id = next(self.ids)
        self.creator = tag.user
        self.user = user
        self.tag = tag
        self.generate_data()
        print("Token" + succes, self)

    def __str__(self):
        return f"TOKEN({self.id}, {self.creator.id}, {self.token}, {self.user}, {self.right}, {self.tag})"
    
    def generate_data(self):
        self.token = uuid.uuid4()
        self.right = random.choice(["R", "W"])

    @staticmethod
    def save_tokens(tokens):
        with open(path + '\\tokens.csv', 'w', newline='') as csvfile:
            fieldnames = ['ID', 'TOKEN', 'USER', 'RIGHT', 'TAG', 'CREATORID']
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
            writer.writeheader()
            for token in tokens:
                user_id = token.user.id if token.user else None
                writer.writerow({'ID': token.id, 'TOKEN': token.token, 'USER': user_id, 'RIGHT': token.right, 'TAG': token.tag.id, 'CREATORID': token.creator.id})

def innit_data(nb_users, nb_tags, nb_images):
    users_list = [users() for _ in range(nb_users)]
    tags_list = [tags(user, True) for user in users_list]
    tags_list.extend([tags(random.choice(users_list)) for _ in range(nb_tags)])

    image_list = [images(random.choice(users_list)) for _ in range(nb_images)]
    
    places_list = []
    for user in users_list:
        usable_images = [image for image in image_list if image.user == user or image.public]
        usable_images.append(None)
        places_list_temp = [places(user, random.choice(usable_images)) for _ in range(random.randint(0, 50))]
        places_list.extend(places_list_temp)
    
    places_tags_list = []
    for place in places_list:
        tags_user = [tag for tag in tags_list[nb_users:] if tag.user == place.user]
        places_tags_list_temp = [places_tags(place) for _ in range(random.randint(0, len(tags_user)))]
        for place_tag in places_tags_list_temp:
            tag = random.choice(tags_user)
            while not place_tag.add_tag(tag):
                tag = random.choice(tags_user)
            tags_user.remove(tag)

        places_tags_list.extend(places_tags_list_temp)

    tokens_list = [tokens(tag, user) for tag in tags_list[nb_users:] for user in users_list if tag.user.id != user.id]

    return users_list, places_list, tags_list, places_tags_list, tokens_list, image_list

users_list, places_list, tags_list, places_tags_list, tokens_list, image_list = innit_data(100, 50, 10)

users.save_users(users_list)
tags.save_tags(tags_list)
places_tags.save_places_tags(places_tags_list)
tokens.save_tokens(tokens_list)
places.save_places(places_list)
images.save_images(image_list)

print("Data created successfully")