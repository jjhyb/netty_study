package com.yibo.thrift;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;

/**
 * @author: huangyibo
 * @Date: 2019/3/13 23:53
 * @Description:
 */
public class ThriftClient {

    public static void main(String[] args) {
        TTransport transport = new TFramedTransport(new TSocket("localhost",8899),1000);
        TProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);

        try {
            transport.open();//打开socket
            Person person = client.getPersonByUsername("张三");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("-------------------------");

            Person person1 = new Person();
            person1.setUsername("李四");
            person1.setAge(20);
            person1.setMarried(false);
            client.savePerson(person1);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        } finally {
            transport.close();
        }
    }
}
