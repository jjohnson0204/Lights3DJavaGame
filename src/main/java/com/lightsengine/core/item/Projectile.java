package com.lightsengine.core.item;

import com.lightsengine.core.entity.enemy.Enemy;
import org.joml.Vector3f;

public class Projectile {
    public Vector3f position;
    public Vector3f velocity;
    int Damage;
    public void TriggerCollision( Enemy target)
    {
//        target.hp-=Damage;
    }
    public void Move()
    {
//        position+=velocity;
    }
}
