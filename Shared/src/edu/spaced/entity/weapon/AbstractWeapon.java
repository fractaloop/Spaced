package edu.spaced.entity.weapon;

import java.sql.Time;

import com.badlogic.gdx.math.Vector2;

import edu.spaced.simulation.entity.Entity;

public class AbstractWeapon extends Entity{
   private Integer cur_ammo = 0;
   private Integer max_ammo = 0;

public void fire(Integer X, Integer Y, Vector2 velo, Time firetime){
	if(cur_ammo>0){
		//fire
		//play fire sound here?
		cur_ammo--;
		
	}
	else{
		//play can't fire sound.
	}
}
   
public void reload(){
	this.cur_ammo = this.max_ammo;
}
public void render(){
   // no idea how to do this.
}
   
   
   
public Integer getCur_ammo() {
	return cur_ammo;
}
public void setCur_ammo(Integer cur_ammo) {
	this.cur_ammo = cur_ammo;
}
public Integer getMax_ammo() {
	return max_ammo;
}
public void setMax_ammo(Integer max_ammo) {
	this.max_ammo = max_ammo;
}
}
