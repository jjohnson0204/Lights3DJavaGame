package com.lightsengine.core.item.weapon.guns;

public class M16 {
    private Integer weaponDamage = 5;
    private Integer weaponAmmo = 25;
    private String weaponName = "M16";

    public M16(String name, int ammo, int damage) {
        name = weaponName;
        ammo = weaponAmmo;
        damage = weaponDamage;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public Integer getAmmo() {
        return weaponAmmo;
    }

    public Integer getDamage() {
        return weaponDamage;
    }
}
