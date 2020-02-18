import threading
import random
import time
import asyncio

numberOfEatProcesses = 4
philosophersNumber = 5
grabbing = threading.Lock()
waiter = asyncio.Semaphore(philosophersNumber-1)


class Philosopher(threading.Thread):

    def __init__(self, number, leftFork, rightFork):
        threading.Thread.__init__(self)
        self.number = number
        self.leftFork = leftFork
        self.rightFork = rightFork
        self.eatProcesses = numberOfEatProcesses
        self.totalTime = 0

    def run(self):
        print('Hi! I\'m philosopher #' + str(self.number) + '\n')
        while self.eatProcesses > 0:
            start = time.time()
            # Naive
            """
            self.leftFork.grab()
            print('Philosopher #' + str(self.number) + ' grabs left fork.\n')
            self.rightFork.grab()
            print('Philosopher #' + str(self.number) + ' grabs right fork.\n')
            """
            # Asymetric
            """"
            if self.number % 2 == 0:
                self.rightFork.grab()
                print('Philosopher #' + str(self.number) + ' grabs right fork.\n')
                self.leftFork.grab()
                print('Philosopher #' + str(self.number) + ' grabs left fork.\n')
            else:
                self.leftFork.grab()
                print('Philosopher #' + str(self.number) + ' grabs left fork.\n')
                self.rightFork.grab()
                print('Philosopher #' + str(self.number) + ' grabs right fork.\n')
            """
            # Famine
            """
            while self.leftFork.mutex.locked() and self.rightFork.mutex.locked():
                time.sleep(0.1)
            grabbing.acquire()
            self.leftFork.grab()
            self.rightFork.grab()
            grabbing.release()
            """
            #Waiter
            """
            waiter.acquire()
            self.leftFork.grab()
            self.rightFork.grab()
            print('Philosopher #' + str(self.number) + ' grabs left fork.\n')
            print('Philosopher #' + str(self.number) + ' grabs right fork.\n')
            """
            self.totalTime += (time.time() - start)
            self.eat()
            #waiter.release() # Waiter
            self.leftFork.release()
            print('Philosopher #' + str(self.number) + ' releases left fork.\n')
            self.rightFork.release()
            print('Philosopher #' + str(self.number) + ' releases right fork.\n')
        print(
            'Philosopher #' + str(self.number) + ' ended with avg time: ' + str(self.totalTime / numberOfEatProcesses))

    def eat(self):
        sleepTime = random.randint(1, 4)*1000
        print('Philosopher #' + str(self.number) + ' eats for ' + str(sleepTime) + ' miliseconds\n')
        time.sleep(sleepTime / 1000)
        self.eatProcesses = self.eatProcesses - 1


class Fork:
    def __init__(self, id):
        self.id = id
        self.mutex = threading.Lock()

    def grab(self):
        self.mutex.acquire()

    def release(self):
        self.mutex.release()


def DiningPhilosophers():
    philosophers = []
    forks = []
    for x in range(0, philosophersNumber):
        forks.append(Fork(x))

    for x in range(0, philosophersNumber):
        philosophers.append(Philosopher(x, forks[x], forks[(x + 1) % philosophersNumber]))
        philosophers[x].start()


DiningPhilosophers()
