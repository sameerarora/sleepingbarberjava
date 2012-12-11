package com.xebia.barber;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Barber implements Runnable {

	Queue<Customer> customers;

	private Shop shop;

	public Barber(Shop shop) {
		this.shop = shop;
		customers = new LinkedBlockingQueue<Customer>();
	}

	public void addCustomerToQueue(Customer customer) {
		customers.add(customer);
	}

	public void run() {
		while (!customers.isEmpty()) {
			Customer customer = customers.remove();
			
			performHaircut(customer);
			
			shop.releaseChair();
		}
	}

	private void performHaircut(Customer customer) {
		try {
			Thread.sleep(1000);
			System.out.println("Customer " + customer.getId()
					+ " has got a haircut");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
