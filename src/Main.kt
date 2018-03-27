import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileInputStream


object Main {
    val mainFrame = MainFrame()
    @JvmStatic
    fun main(args: Array<String>) {
        mainFrame.init()
    }
}