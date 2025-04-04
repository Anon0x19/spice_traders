package com.mygdx.pirategame.world;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.pirategame.Hud;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.gameobjects.CannonFire;
import com.mygdx.pirategame.gameobjects.CollegeFire;
import com.mygdx.pirategame.gameobjects.Player;
import com.mygdx.pirategame.gameobjects.enemy.Enemy;
import com.mygdx.pirategame.gameobjects.entity.Entity;
import com.mygdx.pirategame.gameobjects.tileobject.InteractiveTileObject;


/**
 * Tells the game what to do when certain entities come into contact with each other
 *
 * @author Ethan Alabaster, Charlie Crosley, James McNair, Robert Murphy, Marc Perales Salomo
 * @version 1.0
 */
public class WorldContactListener implements ContactListener {

    /**
     * The start of the collision. Tells the game what should happen when the contact begins
     *
     * @param contact The object that contains information about the collision
     */
    @Override
    public void beginContact(Contact contact) {
        // Finds contact, fixA and fixB are the two entities causing collision
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        // Fixes contact to an entity
        switch (cDef) {
            case PirateGame.COIN_BIT | PirateGame.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == PirateGame.COIN_BIT) {
                    ((Entity) fixA.getUserData()).entityContact();
                } else {
                    ((Entity) fixB.getUserData()).entityContact();
                }
                break;
            case PirateGame.ABSORPTION_HEART_BIT | PirateGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.ABSORPTION_HEART_BIT) {
                    ((Entity) fixA.getUserData()).entityContact();
                }
                else {
                    ((Entity) fixB.getUserData()).entityContact();
                }
                break;
            case PirateGame.SPEED_BOOST_BIT | PirateGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.SPEED_BOOST_BIT) {
                    ((Entity) fixA.getUserData()).entityContact();
                }
                else {
                    ((Entity) fixB.getUserData()).entityContact();
                }
                break;
            case PirateGame.FASTER_SHOOTING_BIT | PirateGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.FASTER_SHOOTING_BIT) {
                    ((Entity) fixA.getUserData()).entityContact();
                }
                else {
                    ((Entity) fixB.getUserData()).entityContact();
                }
                break;
            case PirateGame.COIN_MAGNET_BIT | PirateGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.COIN_MAGNET_BIT) {
                    ((Entity) fixA.getUserData()).entityContact();
                }
                else {
                    ((Entity) fixB.getUserData()).entityContact();
                }
                break;
            case PirateGame.TORNADO_BIT | PirateGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == PirateGame.TORNADO_BIT) {
                    ((Entity) fixA.getUserData()).entityContact();
                }
                else {
                    ((Entity) fixB.getUserData()).entityContact();
                }
                break;
            case PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == PirateGame.DEFAULT_BIT) {
                    if (fixA.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(fixA.getUserData().getClass())) {
                        ((InteractiveTileObject) fixA.getUserData()).onContact();
                        ((Player) fixB.getUserData()).playBreakSound();
                    }
                } else {
                    if (fixB.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(fixB.getUserData().getClass())) {
                        ((InteractiveTileObject) fixB.getUserData()).onContact();
                    }
                }
                break;
            case PirateGame.ENEMY_BIT | PirateGame.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == PirateGame.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).onContact();
                } else {
                    ((Enemy) fixB.getUserData()).onContact();
                }
                break;
            case PirateGame.COLLEGE_BIT | PirateGame.CANNON_BIT:
                if (fixA.getFilterData().categoryBits == PirateGame.COLLEGE_BIT) {
                    if (fixA.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(fixA.getUserData().getClass())) {
                        ((InteractiveTileObject) fixA.getUserData()).onContact();
                        ((CannonFire) fixB.getUserData()).setToDestroy();
                    }
                } else {
                    if (fixB.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(fixB.getUserData().getClass())) {
                        ((InteractiveTileObject) fixB.getUserData()).onContact();
                        ((CannonFire) fixA.getUserData()).setToDestroy();
                    }
                }
                break;
            case PirateGame.ENEMY_BIT | PirateGame.CANNON_BIT:
                if (fixA.getFilterData().categoryBits == PirateGame.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).onContact();
                    ((CannonFire) fixB.getUserData()).setToDestroy();
                } else {
                    ((Enemy) fixB.getUserData()).onContact();
                    ((CannonFire) fixA.getUserData()).setToDestroy();
                }
                break;
            // Player collision with college cannonball
            case PirateGame.COLLEGE_FIRE_BIT | PirateGame.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == PirateGame.COLLEGE_FIRE_BIT) {
                    Hud.changeHealth(-15);
                    ((CollegeFire) fixA.getUserData()).setToDestroy();
                } else {
                    Hud.changeHealth(-15);
                    ((CollegeFire) fixB.getUserData()).setToDestroy();
                    ((Player) fixA.getUserData()).playCannonballHitSound();

                }
                break;
            // enemy collides with enemy
            case PirateGame.ENEMY_BIT | PirateGame.ENEMY_BIT:
                // notifying a single ship so it can pause
                ((Enemy) fixB.getUserData()).onContactOther();
                break;
        }
    }

    /**
     * Run when contact is ended. Nearly empty since nothing special needs to happen when a contact is ended
     *
     * @param contact The object that contains information about the collision
     */
    @Override
    public void endContact(Contact contact) {
        // Displays contact message
    }

    /**
     * (Not Used)
     * Can be called before beginContact to pre emptively solve it
     *
     * @param contact     The object that contains information about the collision
     * @param oldManifold Predicted impulse based on old data
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    /**
     * (Not Used)
     * Can be called before beginContact to post emptively solve it
     *
     * @param contact The object that contains information about the collision
     * @param impulse The signal recieved
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
