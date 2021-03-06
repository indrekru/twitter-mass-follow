package com.ruubel.massfollow.job

import com.ruubel.massfollow.model.FollowingAmount
import com.ruubel.massfollow.service.FollowingAmountService
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

class FollowStatsCleanupJobSpec extends Specification {

    FollowStatsCleanupJob job
    FollowingAmountService followingAmountService

    def setup() {
        followingAmountService = Mock(FollowingAmountService)
        job = new FollowStatsCleanupJob(followingAmountService)
    }

    def "when cleanup job runs, then only selected ones are removed from DB" () {
        given:
            List<FollowingAmount> list = new ArrayList<FollowingAmount>(){{
                add(new FollowingAmount(id: 1, created: Instant.now()))
                add(new FollowingAmount(id: 2, created: Instant.now().plusSeconds(60)))
                add(new FollowingAmount(id: 3, created: Instant.now().plusSeconds(75)))
                add(new FollowingAmount(id: 4, created: Instant.now().plus(1, ChronoUnit.DAYS)))
                add(new FollowingAmount(id: 5, created: Instant.now().plus(1, ChronoUnit.DAYS).plusSeconds(60)))
                add(new FollowingAmount(id: 6, created: Instant.now().plus(2, ChronoUnit.DAYS)))
            }}
        when:
            job.cleanup()
        then:
            1 * followingAmountService.findByCreatedLessThanOrderByCreatedAsc(_) >> []
            1 * followingAmountService.findByCreatedGreaterThanOrderByCreatedAsc(_) >> list
            1 * followingAmountService.delete(list[0])
            1 * followingAmountService.delete(list[1])
            1 * followingAmountService.delete(list[3])
    }

}