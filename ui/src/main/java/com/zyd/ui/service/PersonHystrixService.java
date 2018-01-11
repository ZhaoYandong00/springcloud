package com.zyd.ui.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zyd.ui.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonHystrixService {
    @Autowired
    PersonService personService;

    /**
     * 保存，本方法失败，调用后备方法fallbackSave
     *
     * @param name 名字
     * @return 所有人员
     */
    @HystrixCommand(fallbackMethod = "fallbackSave")
    public List<Person> save(String name) {
        return personService.save(name);
    }

    /**
     * 后备方法
     * @param name 名字
     * @return 失败人员
     */
    public List<Person> fallbackSave(String name) {
        List<Person> list = new ArrayList<>();
        Person p = new Person(name + "没有保存成功，Person Service 故障");
        list.add(p);
        return list;
    }
}
