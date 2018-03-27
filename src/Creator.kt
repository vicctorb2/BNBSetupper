import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.swing.JOptionPane


class Creator @Throws(IOException::class)
constructor() {
    internal var setupIniFile = File("./res/ST_BNB_Install_45.00/ST_BNB_Install_45.00/Setup.ini")
    internal var directory = File("./res/ST_BNB_Install_45.00")
    val akt10File = File("./res/ST_BNB_Install_45.00/ST_BNB_Install_45.00/AKT10.docx")
    val document = XWPFDocument(FileInputStream(akt10File))
    var writer = OutputStreamWriter(FileOutputStream(setupIniFile, true), "cp1251")
    @Throws(IOException::class)
    fun createSetup(naimenovanie: String, fio_dir: String, fio_buh: String, dolj: String, unp: String, adres: String, vn_cod: String, bank: String, vn2: String, pathForZipArchive: String) {
        val fileWriter = FileWriter(setupIniFile, false)
        writer.write("[Install]\r\n" +
                "InstallPath=D:\\ST-BNB\\\r\n" +
                "InstallMode=0\r\n" +
                "EngineName=HBServer\r\n" +
                "MailType=SMTP/POP3\r\n" +
                "\r\n" +
                "TYPE=FIREBIRD\r\n" +
                "ExecFile=HBClient_BNB.exe\r\n" +
                "NameForShortcut=BNB-bank\r\n" +
                "[Params]\r\n" +
                "QuickPrint=1\r\n" +
                "FineReport=1\r\n" +
                "[ListPhones]\r\n" +
                "\r\n" +
                "[Passport]\r\n" +
                "ID=" + vn_cod + "\r\n" +
                "UNN=" + unp + "\r\n" +
                "Name=" + naimenovanie + "\r\n" +
                "Face1=" + fio_dir + "\r\n" +
                "Face2=" + fio_buh + "\r\n" +
                "AppFace1=" + dolj + "\r\n" +
                "\r\n" +
                "\r\n" +
                "MFO=765\r\n" +
                "NameBank=ОАО \"БНБ-Банк\"\r\n" +
                "Address=" + adres + "\r\n" +
                "\r\n" +
                "[Accounts]\r\n" +
                "Oper=1\r\n" +
                "\r\n" +
                "[MailSMTP]\r\n" +
                "SMTP=*confidential*\r\n" +
                "POP3=*confidential*\r\n" +
                "EMailClient=" + vn2 + "@*confidential*\r\n" +
                "EMailBank=" + bank + "\r\n" +
                "MailFolder=.\\MailBox\\\r\n" +
                "PathHBFiles=.\\FilesFromBank\\\r\n" +
                "IsLocalMailFolder=1\r\n" +
                "MailAccount=" + vn2 + "@*confidential*\r\n" +
                "\r\n" +
                "LogTime=30\r\n" +
                "LogFile=.\\Log\\HB_SMTP.log\r\n" +
                "\r\n" +
                "[Cryptography]\r\n" +
                "CryptFolder=.\\res\\\r\n" +
                "Setup1=HBInstCryptoProvider.exe #prov=Avest CSP Base #inst=setupAvCSP6.1.0.741.exe /devices=avtoken\r\n" +
                "Setup2=AvPCMEx_setup.exe /dir=.\\Avest\\AvPCM /SILENT\r\n" +
                "\r\n" +
                "[Finish]\r\n" +
                "IsInstalled=0\r\n" +
                "\r\n")
        writer.close()
        println("Setup.ini for $naimenovanie created")
        val outputZipName = "ST_BNB_Install_45.00_" + naimenovanie
        val outputZip = File(pathForZipArchive + outputZipName + ".zip")
        var t = Thread(Runnable {
            var answer = JOptionPane.showConfirmDialog(null, "Cоздать приложение-акт 10?")
            if (answer == JOptionPane.OK_OPTION) {
                var regNumber = JOptionPane.showInputDialog("Введите рег. номер клиента")
                createAKT10(regNumber)
            }
            directoryToZip(directory, outputZip)
        })
        t.start()
    }

    @Throws(IOException::class)
    private fun directoryToZip(directory: File, zipFile: File) {
        var directory = directory
        val base = directory.toURI()
        val queue = LinkedList<File>()
        queue.push(directory)
        val out = FileOutputStream(zipFile)
        var res: Closeable = out

        try {
            val zout = ZipOutputStream(out)
            res = zout
            while (!queue.isEmpty()) {
                directory = queue.pop()
                for (child in directory.listFiles()!!) {
                    var name = base.relativize(child.toURI()).path
                    if (child.isDirectory) {
                        queue.push(child)
                        name = if (name.endsWith("/")) name else name + "/"
                        zout.putNextEntry(ZipEntry(name))
                    } else {
                        zout.putNextEntry(ZipEntry(name))


                        val `in` = FileInputStream(child)
                        try {
                            val buffer = ByteArray(1024)
                            while (true) {
                                val readCount = `in`.read(buffer)
                                if (readCount < 0) {
                                    break
                                }
                                zout.write(buffer, 0, readCount)
                            }
                        } finally {
                            `in`.close()
                        }
                        zout.closeEntry()
                    }
                }
            }
        } finally {
            res.close()
        }
        val date = Date()
        JOptionPane.showMessageDialog(Main.mainFrame, "Архив успешно создан и помещен в $zipFile")
    }

    fun createAKT10(regNumber: String) {
        var date = Date()
        var dateFormatter = SimpleDateFormat("«dd» MMMM yyyy года.", Locale("ru"))
        var dateString = dateFormatter.format(date).toString()
        val outputStream = FileOutputStream(akt10File)
        var paragraphText = "№$regNumber/КБ от $dateString"
        var paragraps: MutableList<XWPFParagraph> = document.paragraphs
        var newParagraph = document.createParagraph()
        newParagraph.alignment = ParagraphAlignment.CENTER
        var run = newParagraph.createRun()
        run.setText(paragraphText)
        run.fontSize = 9
        document.setParagraph(newParagraph, 8)
        document.write(outputStream)
        outputStream.close()
    }

}
