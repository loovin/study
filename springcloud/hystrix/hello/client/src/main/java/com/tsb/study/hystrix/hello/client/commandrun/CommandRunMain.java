package com.tsb.study.hystrix.hello.client.commandrun;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;
import rx.Observer;

public class CommandRunMain {
    public static void main(String s[]) throws Exception{
        RunCommand c1 = new RunCommand("observe method");
        Observable<String> ob1 =c1.observe();
        ob1.subscribe(new Observer<String>() {
            public void onCompleted() {

            }

            public void onError(Throwable throwable) {

            }

            public void onNext(String s) {
                System.out.println("observe next:"+s);
            }
        });

        RunCommand c2 = new RunCommand("toObservable method");
        Observable<String> ob = c2.toObservable();
        ob.subscribe(new Observer<String>() {
            public void onCompleted() {
                System.out.println("toObservable completed");
            }

            public void onError(Throwable throwable) {
                System.out.println("toObservable error");
            }

            public void onNext(String s) {
                System.out.println(s);
            }
        });

        Thread.sleep(1000);
    }

    static class RunCommand extends HystrixCommand<String> {
        String message;
        public RunCommand(String message){
            super(HystrixCommandGroupKey.Factory.asKey("TestGroup"));
            this.message = message;
        }
        protected String run() throws Exception {
            System.out.println(message);
            return "success";
        }
    }
}
