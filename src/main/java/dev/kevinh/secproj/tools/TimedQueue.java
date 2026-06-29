package dev.kevinh.secproj.tools;

import java.util.ArrayDeque;

public class TimedQueue {
  private ArrayDeque<Long> queue;
  private Long window = 1_000_000_000L;

  public TimedQueue(Long window) {
    this.queue = new ArrayDeque<Long>();
    this.window = window;
  }

  // use the 1s version
  public TimedQueue() {
    this.queue = new ArrayDeque<Long>();
  }

  public void addEventNow() {
    this.queue.add(System.nanoTime());
  }

  public void refreshQueue() {
    long cutoff = (System.nanoTime() - window);
    Long oldest;

    while ((oldest = this.queue.peek()) != null && oldest < cutoff) {
      this.queue.pop();
    }
  }

  public int size() {
    this.refreshQueue();
    return queue.size();
  }
}
