package fi.taphold.lightsout.entities;

/**
 * @author Jarno Lahti.
 *
 * base class for entity that has to interact with world physics
 * has collision point at bottom to check if its grounded or not
 */
public class DynamicEntity extends Entity{
    public static final int GROUND_COLLISION_POINT_HEIGHT = 5;

    private boolean grounded;
    private boolean groundedSet = false;

    public DynamicEntity(){
        super();
    }

    public void setGroundedCheck(){
        if(!groundedSet) {
            groundedSet = true;
        }
    }

    public void setGrounded(){
        if(groundedSet){
            grounded = true;
        }else {
            grounded = false;
        }
        groundedSet = false;
    }

    protected boolean isGrounded(){
        return grounded;
    }

    /**
     * define what happens when collision in world occurs
     */
    public void onHorizontialCollide(){

    }

    public void onVerticalCollide(){

    }
}
