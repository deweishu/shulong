package com.qkl.user.jpa;

import com.qkl.user.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface FeedbackRepository extends JpaRepository<Feedback, String>, JpaSpecificationExecutor<Feedback> {

}
