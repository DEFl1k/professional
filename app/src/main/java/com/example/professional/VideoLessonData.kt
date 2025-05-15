package com.example.professional

data class Lessons(
    val urls: List<String>,
    val text: List<String>
)

fun getLessonsData(code: String): Lessons {
    return when (code) {
        "A" -> {
            Lessons(
                text = listOf("Габаритная восьмерка","Скоростное маневрирование","Габаритный коридор","Змейка"),
                urls = listOf(
                    "https://rutube.ru/play/embed/d888b44feb982335bfbc46e68cab7488",
                    "https://rutube.ru/play/embed/c30e1a05cde1f6724b2d76ba542132a4",
                    "https://rutube.ru/play/embed/441dd7bf12dc43fdb5fbb2bae373b2bc",
                    "https://rutube.ru/play/embed/84dfcadec8162c29fe4e2f1872c4144c"
                )
            )
        }
        "B" -> {
            Lessons(
                text = listOf("Парковка под углом 90 градусов (заезд в гараж)"),
                urls = listOf(
                    "https://rutube.ru/play/embed/ab971a2849095786ae7ef79b9e70d6c6"
                )
            )
        }
        "C" -> {
            Lessons(
                text = listOf("Остановка и начало движения на подъеме и спуске","Прямолинейное движение задним ходом для постановке к погрузке/разгрузке на площадку","Парковка под углом 90 градусов","Параллельная парковка задним ходом", "Научим водить грузовую технику любого !"),
                urls = listOf(
                    "https://rutube.ru/play/embed/4355fc8debf6a8c6ea99bce3b9edb2e5",
                    "https://rutube.ru/play/embed/ce802f7d55a9b0cc4621822de5163c9d",
                    "https://rutube.ru/play/embed/33739752e7033bcebee5433a918764e9",
                    "https://rutube.ru/play/embed/f160d7b56d52dd29f074596e24de7a08",
                    "https://rutube.ru/play/embed/75f0980a7a6e023d39f41a67d90de5b1"
                )
            )
        }
        "CE" -> {
            Lessons(
                text = listOf("Упражнения на автодроме для категории CE"),
                urls = listOf(
                    "https://rutube.ru/play/embed/24db2169823269b892883918800df0f4"
                )
            )
        }
        "D" -> {
            Lessons(
                text = listOf("Упражнения по вождению автобуса на закрытой площадке автодрома"),
                urls = listOf(
                    "https://rutube.ru/play/embed/e7e3fe4048fc5bdc26fcfbae276cd3b1",
                    "https://rutube.ru/play/embed/c30e1a05cde1f6724b2d76ba542132a4",
                    "https://rutube.ru/play/embed/441dd7bf12dc43fdb5fbb2bae373b2bc",
                    "https://rutube.ru/play/embed/84dfcadec8162c29fe4e2f1872c4144c"
                )
            )
        }
        "JCB" -> {
            Lessons(
                text = listOf("Остановка и начало движения на подъеме"),
                urls = listOf(
                    "https://rutube.ru/play/embed/5415472c3da3466317a8bf487864264d"
                )
            )
        }
        "BE" -> {
            Lessons(
                text = listOf("Остановка и трогание на подъеме и спуске, параллельная парковка задним ходом с прицепом, парковка с прицепом под углом 90 градусов, сцеп-расцеп транспортного средства и прицепа"),
                urls = listOf(
                    "https://rutube.ru/play/embed/5b59d51d6feada0c9a4157a506b57d0e"
                )
            )
        }
        "A1" -> {
            Lessons(
                text = listOf("Начало движения, остановка и трогание на подъеме и спуске, разворот в ограниченном пространстве и парковка под углом 90 градусов, змейка"),
                urls = listOf(
                    "https://rutube.ru/play/embed/48952a777351c2696c54351319f7cf2d"
                )
            )
        }
        else -> Lessons(
            text = listOf(),
            urls = listOf()
        )
    }
}
