package co.touchlab.droidcon.viewmodel.session

import co.touchlab.droidcon.domain.entity.Session
import co.touchlab.droidcon.domain.gateway.SessionGateway
import co.touchlab.droidcon.domain.service.ConferenceConfigProvider
import co.touchlab.droidcon.domain.service.DateTimeService

class ScheduleViewModel(
    private val sessionGateway: SessionGateway,
    sessionDayFactory: SessionDayViewModel.Factory,
    private val sessionDetailFactory: SessionDetailViewModel.Factory,
    sessionDetailScrollStateStorage: SessionDetailScrollStateStorage,
    dateTimeService: DateTimeService,
    conferenceConfigProvider: ConferenceConfigProvider,
) : BaseSessionListViewModel(
    sessionGateway,
    sessionDayFactory,
    sessionDetailFactory,
    sessionDetailScrollStateStorage,
    dateTimeService,
    conferenceConfigProvider,
    attendingOnly = false,
) {

    fun openSessionDetail(sessionId: Session.Id) {
        lifecycle.whileAttached {
            val sessionItem = sessionGateway.getScheduleItem(sessionId) ?: return@whileAttached
            presentedSessionDetail = sessionDetailFactory.create(sessionItem)
        }
    }

    class Factory(
        private val sessionGateway: SessionGateway,
        private val sessionDayFactory: SessionDayViewModel.Factory,
        private val sessionDetailFactory: SessionDetailViewModel.Factory,
        private val sessionDetailScrollStateStorage: SessionDetailScrollStateStorage,
        private val dateTimeService: DateTimeService,
        private val conferenceConfigProvider: ConferenceConfigProvider,
    ) {

        fun create() = ScheduleViewModel(
            sessionGateway,
            sessionDayFactory,
            sessionDetailFactory,
            sessionDetailScrollStateStorage,
            dateTimeService,
            conferenceConfigProvider,
        )
    }
}
