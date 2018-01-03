package com.github.mathiewz.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Optional;

class EnumConverterTest {

    private EnumConverter<TestEnum> converter = new EnumConverter<>(TestEnum.class);

    private enum TestEnum{
        ONE("One"),
        TWO("Two"),
        THREE("Three");

        private final String libelle;

        TestEnum(String libelle){
            this.libelle = libelle;
        }

        public String getLibelle() {
            return libelle;
        }
    }

    @Test
    @DisplayName("Empty when no entry")
    void it_should_return_empty_optionnal_when_there_are_no_corresponding_entry(){
        //GIVEN a non existing enum entry libelle
        String libelle = "Four";

        //WHEN EnumConverter.getByGetter is called
        Optional<TestEnum> result = EnumConverter.getByGetter(TestEnum.class, TestEnum::getLibelle, libelle);

        //THEN an empty optionnal should be returned
        Assertions.assertFalse(result.isPresent());
    }

    @DisplayName("Static by getter")
    @ParameterizedTest(name = "Entry {0}")
    @EnumSource(TestEnum.class)
    void it_should_return_optionnal_containing_the_entry_when_there_is_corresponding_entry_by_value(TestEnum entry){
        //GIVEN an existing enum entry libelle
        String libelle = entry.libelle;

        //WHEN EnumConverter.getByGetter is called
        Optional<TestEnum> result = EnumConverter.getByGetter(TestEnum.class, TestEnum::getLibelle, libelle);

        //THEN an empty optionnal should be returned
        Assertions.assertAll(
                () -> Assertions.assertTrue(result.isPresent()),
                () -> Assertions.assertEquals(result.get(), entry)
        );
    }

    @DisplayName("Static by name")
    @ParameterizedTest(name = "Entry {0}")
    @EnumSource(TestEnum.class)
    void it_should_return_optionnal_containing_the_entry_when_there_is_corresponding_entry_by_name(TestEnum entry){
        //GIVEN an existing enum entry name
        String name = entry.name();

        //WHEN EnumConverter.getByGetter is called
        Optional<TestEnum> result = EnumConverter.getByName(TestEnum.class, name);

        //THEN an empty optionnal should be returned
        Assertions.assertAll(
                () -> Assertions.assertTrue(result.isPresent()),
                () -> Assertions.assertEquals(result.get(), entry)
        );
    }

    @DisplayName("Static by getter")
    @ParameterizedTest(name = "Entry {0}")
    @EnumSource(TestEnum.class)
    void it_should_return_optionnal_containing_the_entry_when_there_is_corresponding_entry_by_value_via_instanced_converter(TestEnum entry){
        //GIVEN an existing enum entry libelle
        EnumConverter<TestEnum> converter = new EnumConverter<>(TestEnum.class);
        String libelle = entry.libelle;

        //WHEN EnumConverter.getByGetter is called
        Optional<TestEnum> result = converter.getByGetter(TestEnum::getLibelle, libelle);

        //THEN an empty optionnal should be returned
        Assertions.assertAll(
                () -> Assertions.assertTrue(result.isPresent()),
                () -> Assertions.assertEquals(result.get(), entry)
        );
    }

    @DisplayName("Instanced by name")
    @ParameterizedTest(name = "Entry {0}")
    @EnumSource(TestEnum.class)
    void it_should_return_optionnal_containing_the_entry_when_there_is_corresponding_entry_by_name_via_instanced_converter(TestEnum entry){
        //GIVEN an existing enum entry name
        EnumConverter<TestEnum> converter = new EnumConverter<>(TestEnum.class);
        String name = entry.name();

        //WHEN EnumConverter.getByGetter is called
        Optional<TestEnum> result = converter.getByName(name);

        //THEN an empty optionnal should be returned
        Assertions.assertAll(
                () -> Assertions.assertTrue(result.isPresent()),
                () -> Assertions.assertEquals(result.get(), entry)
        );
    }

}