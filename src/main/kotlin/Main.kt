import org.apache.commons.codec.digest.DigestUtils
import java.io.File


fun main(args: Array<String>) {
    val md5s = File("md5.txt").readLines().toHashSet()

    val builder = StringBuilder()

    fun getLength(number: Int): Int {
        var length = 0
        var temp = 1
        while (temp <= number) {
            length++
            temp *= 10
        }
        return length
    }

    // O(log_10) = 거의 상수
    fun toText(text: Int, count: Int): String {
        val length = getLength(text)

        builder.setLength(0)
        for (j in 0 until count - length) {
            builder.append('0')
        }
        builder.append(text)

        return builder.toString()
    }

    val start = System.currentTimeMillis()
    val map = HashMap<String, String>()

    var count = 0
    // O(50조)
    // O(50_0000_0000_0000)
    for (year in 17..22) {
        // O(10조)
        for (number in 0 until 1_00000) {
            val numberText = toText(number, 5)

            // 40만 초
            // 111시간
            // 140 / 10
            count++
            if (count >= 1000) {
                count = 0
                println("$number ${System.currentTimeMillis() - start}")
            }

            // O(1억)
            for (phoneFirst in 0 until 1_0000) {
                val phoneFirstText = toText(phoneFirst, 4)

                for (phoneSecond in 0 until 1_0000) {
                    val phoneSecondText = toText(phoneSecond, 4)

                    // 자리수 10^14
                    // format = 20*******      010-****             -****
                    val text = "20${year}${numberText}010-${phoneFirstText}-${phoneSecondText}"
                    val md5 = DigestUtils.md5Hex(text)

                    if (md5s.contains(md5)) {
                        println("$text: $md5")

                        synchronized(map) {
                            map[text] = md5
                        }
                    }
                }
            }
        }
    }

    map.forEach { (t, u) ->
        println("$t: $u")
    }
}