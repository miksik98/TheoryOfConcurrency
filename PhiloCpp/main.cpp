#include <iostream>
#include <mutex>
#include <thread>
#include <sys/time.h>
#include <cstdlib>
#include <stdlib.h>
#include <windows.h>
#include <semaphore.h>

using namespace std;

int numberOfEatProcesses = 5;
int philosophersNumber = 5;
mutex grabbing;
sem_t waiter;

class Fork{
public:
    int id;
    mutex sem;
    Fork(): id(-1){}
    Fork(int id1){
        id = id1;
    }
    void grab(){sem.lock();}
    void release(){sem.unlock();}

};

class Philosopher{
public:
    int number;
    Fork *leftFork;
    Fork *rightFork;
    int eatProcesses;
    long int totalTime;
    void eat(){
        int sleepTime = (rand() % 4 + 1)*1000;
        cout<<"Philosopher #" << number << " eats for " << sleepTime << endl;
        Sleep(sleepTime);
        eatProcesses--;
    }
    thread spawn(){
        return thread( [this]{run();});
    }

public:
    Philosopher(): number(-1),leftFork(NULL), rightFork(NULL), eatProcesses(numberOfEatProcesses), totalTime(0){}
    Philosopher(int num, Fork *lf, Fork *rf){
        number = num;
        leftFork = lf;
        rightFork = rf;
        eatProcesses = numberOfEatProcesses;
        totalTime = 0;
    }

    void run(){
        cout << "Hi! I'm philosopher #" << number << endl;
        struct timeval start, end;
        while(eatProcesses > 0){
            gettimeofday(&start,NULL);
            //Naive
            /*leftFork->grab();
            cout << "Philosopher #" << number << " grabs left fork\n";
            rightFork->grab();
            cout << "Philosopher #" << number << " grabs right fork\n";
            */
            //Asymetric
            /*if(number%2 == 0){
                rightFork->grab();
                cout << "Philosopher #" << number << " grabs right fork\n";
                leftFork->grab();
                cout << "Philosopher #" << number << " grabs left fork\n";
            } else {
                leftFork->grab();
                cout << "Philosopher #" << number << " grabs left fork\n";
                rightFork->grab();
                cout << "Philosopher #" << number << " grabs right fork\n";
            }*/
            //Famine
            bool flag = false;
            while(!flag){
            if(leftFork->sem.try_lock()){
                if(rightFork->sem.try_lock()){
                    flag = true;
                }
                leftFork->sem.unlock();
            }}
            cout << "Philosopher #" << number << " grabs left fork\n";
            cout << "Philosopher #" << number << " grabs right fork\n";
            //Waiter
            /*sem_wait(&waiter);
            leftFork->grab();
            cout << "Philosopher #" << number << " grabs left fork\n";
            rightFork->grab();
            cout << "Philosopher #" << number << " grabs right fork\n";*/
            gettimeofday(&end, NULL);
            totalTime += (end.tv_sec * 1000 + end.tv_usec / 1000)-(start.tv_sec * 1000 + start.tv_usec / 1000);
            eat();
            //sem_post(&waiter); //Waiter
            leftFork->release();
            cout << "Philosopher #" << number << " releases right fork\n";
            rightFork->release();
            cout << "Philosopher #" << number << " releases right fork\n";
        }
        cout << "Philosopher #" << number << " ended with waiting time: " << totalTime/numberOfEatProcesses<< " miliseconds" << endl;

    }

};



int main()
{
    srand(time(NULL));
    sem_init(&waiter, 0, 10);
    Philosopher philosophers[philosophersNumber];
    Fork forks[philosophersNumber];
    thread threads[philosophersNumber];

    for (int i = 0; i < philosophersNumber; i++){
        forks[i].id = i;
    }
    for (int i = 0; i < philosophersNumber; i++){
        philosophers[i].leftFork = &forks[i];
        philosophers[i].rightFork = &forks[(i+1)%philosophersNumber];
        philosophers[i].number = i;
        threads[i] = philosophers[i].spawn();
    }
    for (int i = 0; i < philosophersNumber; i++){
        threads[i].join();
    }
    return 0;
}
