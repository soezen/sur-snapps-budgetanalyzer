package sur.snapps.budgetanalyzer.util;

import sur.snapps.budgetanalyzer.domain.event.Event;

import java.util.Comparator;

/**
 * @author sur
 * @since 12/03/2015
 */
public final class Comparators {

    private Comparators() {}

    public final static Comparator<Event> BY_EVENT_TMS = new Comparator<Event>() {
        @Override
        public int compare(Event event1, Event event2) {
            return event1.getTms().compareTo(event2.getTms());
        }
    };
}
