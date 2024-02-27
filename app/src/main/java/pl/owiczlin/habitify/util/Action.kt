package pl.owiczlin.habitify.util

enum class Action {
    ADD,
    UPDATE,
    COMPLETED,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION,
    SHOW_COMPLETED
}

fun String?.toAction(): Action {
    return when {
        this == "ADD" -> {
            Action.ADD
        }
        this == "UPDATE" -> {
            Action.UPDATE
        }
        this == "COMPLETED" -> {
            Action.COMPLETED
        }
        this == "DELETE" -> {
            Action.DELETE
        }
        this == "DELETE_ALL" -> {
            Action.DELETE_ALL
        }
        this == "UNDO" -> {
            Action.UNDO
        }
        this == "SHOW_COMPLETED" -> {
            Action.SHOW_COMPLETED
        }
        else -> {
            Action.NO_ACTION
        }
    }
}