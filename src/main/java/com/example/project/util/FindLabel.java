package com.example.project.util;

import com.example.project.Entity;
import com.example.project.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.PriorityQueue;

@Component
public class FindLabel {
    @Autowired
    EntityRepository repository;
    double min_distance = 10^8;
    Entity closestEntity = new Entity();
    public void findLabel(double fundFrea, double zrc){
        repository.findAll().forEach(entity -> {
            if (entity.calculateDistance(fundFrea, zrc) < min_distance) {
                min_distance = entity.calculateDistance(fundFrea, zrc);
                closestEntity = entity;
            }
        });
        System.out.println(closestEntity + "- Distance: " + min_distance);

    }
}
