package Main;

import Visitors.StatusChange;

public interface TimerListener {
    StatusChange action();
}
