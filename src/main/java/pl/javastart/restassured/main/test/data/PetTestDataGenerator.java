package pl.javastart.restassured.main.test.data;

import pl.javastart.restassured.main.pojo.pet.Category;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.pojo.pet.Tag;
import pl.javastart.restassured.main.test.data.pet.PetStatus;
import pl.javastart.restassured.main.test.data.pet.PetsCategory;
import pl.javastart.restassured.main.test.data.pet.PetTags;

import java.util.Collections;
import java.util.Random;

public class PetTestDataGenerator extends TestDataGenerator {

    public Pet generatePet() {
        PetsCategory petsCategory = randomPetCategory();
        PetTags petTags = randomPetTag();

        Category category = new Category();
        category.setId(petsCategory.getId());
        category.setName(petsCategory.getCategoryName());

        Tag tag = new Tag();
        tag.setId(petTags.getId());
        tag.setName(petTags.getTagName());

        Pet pet = new Pet();
        pet.setName(faker().gameOfThrones().character());
        pet.setId(faker().number().numberBetween(1, 9999));
        pet.setCategory(category);
        pet.setTags(Collections.singletonList(tag));
        pet.setPhotoUrls(Collections.singletonList(faker().internet().url()));

        PetStatus petStatus = randomPetStatus();
        pet.setStatus(petStatus.getStatus());

        return pet;
    }

    public PetTags randomPetTag() {
        int pick = new Random().nextInt(PetTags.values().length);
        return PetTags.values()[pick];
    }

    public PetsCategory randomPetCategory() {
        int pick = new Random().nextInt(PetsCategory.values().length);
        return PetsCategory.values()[pick];
    }

    public PetStatus randomPetStatus() {
        int pick = new Random().nextInt(PetStatus.values().length);
        return PetStatus.values()[pick];
    }
}
