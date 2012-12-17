package com.xebia.barber;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class BarberShopSimulator {

	Shop shop = new Shop();

	CountDownLatch latch = new CountDownLatch(1);

	Random random = new Random();

	@Before
	public void setUp() {
		shop.init();
	}

	@Test
	public void simulateCustomers() throws Exception {
		sendCustomers();

		latch.await(100, TimeUnit.SECONDS);

		System.out.println(shop.getSuccessfulHaircutCount()
				+ " Customers got a haircut today");
	}

	private void sendCustomers() throws InterruptedException {
		final CyclicBarrier barrier = new CyclicBarrier(5);
		for (int index = 0; index < 5; index++) {
			final int threadCount=index;
			new Thread(new Runnable() {
				public void run() {
					try {
						barrier.await();
						for (int i = 0; i < 20; i++) {
							shop.acquireChair(new Customer((threadCount*i + 1) + ""));
							Thread.sleep(random.nextInt(450));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
