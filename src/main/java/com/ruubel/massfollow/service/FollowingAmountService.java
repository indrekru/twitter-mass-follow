package com.ruubel.massfollow.service;

import com.ruubel.massfollow.dao.FollowingAmountRepository;
import com.ruubel.massfollow.model.FollowingAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FollowingAmountService {

    private FollowingAmountRepository followingAmountRepository;

    @Autowired
    public FollowingAmountService(FollowingAmountRepository followingAmountRepository) {
        this.followingAmountRepository = followingAmountRepository;
    }

    public void saveFollowingAmounts(long imFollowing, long myFollowers) {
        FollowingAmount followingAmount = new FollowingAmount(imFollowing, myFollowers);
        followingAmountRepository.save(followingAmount);
    }

    public List<FollowingAmount> findByCreatedGreaterThan(Instant then) {
        return followingAmountRepository.findByCreatedGreaterThan(then);
    }

    public List<FollowingAmount> findByCreatedLessThan(Instant then) {
        return followingAmountRepository.findByCreatedLessThan(then);
    }

    public void delete(FollowingAmount followingAmount) {
        followingAmountRepository.delete(followingAmount);
    }
}
