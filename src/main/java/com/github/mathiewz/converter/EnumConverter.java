package com.github.mathiewz.converter;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * This class provides method to convert data to an enum entry.
 * All conversions are available through static and instance methods.
 * Static methods have an extra parmeter to specify which enum is targeted.
 * @param <T> The enum class to target
 */
public class EnumConverter<T extends Enum<T>> {

    private Class<T> clazz;

    /**
     * Create a new converter targeting the specified class.
     * @param clazz the target class. must be an enum.
     */
    public EnumConverter(Class<T> clazz){
        this.clazz = clazz;
    }

    /**
     * Get enum entry corresponding to the specified name
     * @param name the name of the entry (case sensitive)
     * @return An optionnal with the entry if it exists, empty otherwise.
     */
    public Optional<T> getByName(String name){
        return EnumConverter.getByName(clazz, name);
    }

    /**
     * Get enum entry corresponding to the specified value.
     * @param <E> the type of the value to convert
     * @param getterReference the method reference returning the value to check.
     * @param value the value to convert.
     * @return An optionnal with the entry if the result of the getterReference is equals to the value parameter.
     */
    public <E> Optional<T> getByGetter(Function<T, E> getterReference, E value){
        return EnumConverter.getByGetter(clazz, getterReference, value);
    }

    /**
     * Get enum entry corresponding to the specified name
     * @param <F> the type of the targeted enum.
     * @param clazz the target class. must be an enum.
     * @param name the name of the entry (case sensitive)
     * @return An optionnal with the entry if it exists, empty otherwise.
     */
    public static <F extends Enum<F>> Optional<F> getByName(Class<F> clazz, String name){
        return getByGetter(clazz, Enum::name, name);
    }

    /**
     * Get enum entry corresponding to the specified value.
     * @param <E> the type of the value to convert
     * @param <F> the type of the targeted enum.
     * @param clazz the target class. must be an enum.
     * @param getterReference the method reference returning the value to check.
     * @param value the value to convert.
     * @return An optionnal with the entry if the result of the getterReference is equals to the value parameter.
     */
    public static <E, F extends Enum<F>> Optional<F> getByGetter(Class<F> clazz, Function<F, E> getterReference, E value){
        return Stream.of(clazz.getEnumConstants())
                .filter(entry -> getterReference.apply(entry).equals(value))
                .findFirst();
    }

}
