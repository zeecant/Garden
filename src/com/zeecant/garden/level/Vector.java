package com.zeecant.garden.level;

public class Vector {
	public float x, y;
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector copy() {
		return new Vector(x, y);
	}
	
	public boolean isZero() {
		return x == 0 && y == 0;
	}
	
	public boolean eq(Vector vec) {
		return Math.abs(this.x - vec.x) <= 1E-6 && Math.abs(this.y - vec.y) <= 1E-6;
	}
	
	//LENGTH
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public float lengthSquared() {
		return x * x + y * y;
	}
	
	//NORMALIZE
	public Vector normalize() {
		float len = this.length();
		return new Vector(x / len, y / len);
	}
	
	public void set(Vector vec) {
		x = vec.x;
		y = vec.y;
	}
	
	//ADDITION
	public Vector add(float x, float y) {
		return new Vector(this.x + x, this.y + y);
	}
	
	public Vector add(Vector vec) {
		return add(vec.x, vec.y);
	}
	
	//SUBTRACTION
	public Vector sub(float x, float y) {
		return new Vector(this.x - x, this.y - y);
	}
	
	public Vector sub(Vector vec) {
		return sub(vec.x, vec.y);
	}
	
	//SCALE
	public Vector scale(float factor) {
		return new Vector(x * factor, y * factor);
	}
	
	//DOT PRODUCT
	public float dot(float x, float y) {
		return x * this.x + y * this.y;
	}
	
	public float dot(Vector vec) {
		return dot(vec.x, vec.y);
	}
	
	//DOT PRODUCT
	public float cross(float x, float y) {
		return this.x * y - this.x * x;
	}
	
	public float cross(Vector vec) {
		return cross(vec.x, vec.y);
	}
	
	//ROTATION
	public Vector rotate(float rad) {
		float cos = (float) Math.cos(rad);
		float sin = (float) Math.sin(rad);
		return new Vector(cos * x - sin * y, sin * x + cos * y);
	}
	
	public Vector rotateDeg(float deg) {
		return rotate((float) Math.toRadians(deg));
	}
	
	public void rotateThis(float rad) {
		float cos = (float) Math.cos(rad);
		float sin = (float) Math.sin(rad);
		float x = this.x;
		this.x = cos * x - sin * y;
		this.y = sin * x + cos * y;
	}
	
	//DIRECTION TO
	public float directionTo(Vector to) {
		return (float) Math.atan2(to.y - y, to.x - x);
	}
	
	public float directionToDeg(Vector to) {
		return (float) Math.toDegrees(directionTo(to));
	}
	
	//FACING
	public float angle() {
		return (float) Math.atan2(y, x);
	}
	
	public float angleDeg() {
		return (float) Math.toDegrees(angle());
	}
	
	//ORTHOGONAL VECTOR
	public Vector orthog() {
		return new Vector(y, -x);
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
