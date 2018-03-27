import java.awt.*
import java.awt.event.ActionEvent
import javax.swing.*


class MainFrame : JFrame() {
    var mainpanel = JPanel()
    var naim_tf = JTextField()
    var adress_tf = JTextField()
    var unp_tf = JTextField()
    var vn_cod_tf = JTextField()
    var fio_leader_tf = JTextField()
    var dolj_leader_tf = JTextField()
    var fio_buh_tf = JTextField()
    var selectPathButton = JButton("Select path")
    var createButton = JButton("Create setup")
    val naim_label = JLabel("Наименование организации:")
    val adress_label = JLabel("Юридический адрес:")
    val unp_label = JLabel("УНП организации:")
    val vn_cod_label = JLabel("Внутренний код:")
    val fio_leader_label = JLabel("ФИО руководителя:")
    val dolj_leader_label = JLabel("Должность руководителя")
    val fio_buh_label = JLabel("ФИО гл. бухгалтера:")
    var bank = ""
    var bank4 = JLabel("")
    var bank5 = JLabel("")
    var vn_cod = ""
    var pathForZipArchive = ""
    var creator = Creator()
    fun init() {
        this.size = Dimension(400, 500)
        this.isResizable = false
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setLocationRelativeTo(null)
        this.title = "BNB Setupper"
        val orangeColor = Color(248, 152, 40)
        var icon = ImageIcon("./res/bnb.png")
        var icon1 = icon.image
        this.iconImage = icon1
        naim_label.font = Font("Candara", Font.CENTER_BASELINE, 15)
        naim_label.horizontalAlignment = Component.CENTER_ALIGNMENT.toInt()
        naim_label.foreground = orangeColor
        adress_label.font = Font("Candara", Font.CENTER_BASELINE, 15)
        adress_label.horizontalAlignment = Component.CENTER_ALIGNMENT.toInt()
        adress_label.foreground = orangeColor
        unp_label.font = Font("Candara", Font.CENTER_BASELINE, 15)
        unp_label.horizontalAlignment = Component.CENTER_ALIGNMENT.toInt()
        unp_label.foreground = orangeColor
        vn_cod_label.font = Font("Candara", Font.CENTER_BASELINE, 15)
        vn_cod_label.horizontalAlignment = Component.CENTER_ALIGNMENT.toInt()
        vn_cod_label.foreground = orangeColor
        fio_leader_label.font = Font("Candara", Font.CENTER_BASELINE, 15)
        fio_leader_label.horizontalAlignment = Component.CENTER_ALIGNMENT.toInt()
        fio_leader_label.foreground = orangeColor
        dolj_leader_label.font = Font("Candara", Font.CENTER_BASELINE, 15)
        dolj_leader_label.horizontalAlignment = Component.CENTER_ALIGNMENT.toInt()
        dolj_leader_label.foreground = orangeColor
        fio_buh_label.font = Font("Candara", Font.CENTER_BASELINE, 15)
        fio_buh_label.horizontalAlignment = Component.CENTER_ALIGNMENT.toInt()
        fio_buh_label.foreground = orangeColor
        selectPathButton.background = Color.WHITE
        selectPathButton.foreground = orangeColor
        createButton.background = orangeColor
        createButton.foreground = Color.WHITE
//        mainpanel.background = orangeColor
        createButton.isEnabled = false
        mainpanel.layout = GridLayout(17, 1)
        mainpanel.add(naim_label)
        mainpanel.add(naim_tf)
        mainpanel.add(adress_label)
        mainpanel.add(adress_tf)
        mainpanel.add(unp_label)
        mainpanel.add(unp_tf)
        mainpanel.add(vn_cod_label)
        mainpanel.add(vn_cod_tf)
        mainpanel.add(fio_leader_label)
        mainpanel.add(fio_leader_tf)
        mainpanel.add(dolj_leader_label)
        mainpanel.add(dolj_leader_tf)
        mainpanel.add(fio_buh_label)
        mainpanel.add(fio_buh_tf)
        mainpanel.add(bank5)
        mainpanel.add(selectPathButton)
        mainpanel.add(createButton)
        this.contentPane = mainpanel
        this.isVisible = true
        createButton.addActionListener { e: ActionEvent ->
            //lambda start
            if (naim_tf.getText() != "" && adress_tf.getText() != "" && unp_tf.getText() != "" && vn_cod_tf.getText() != "" && fio_leader_tf.getText() != "" && dolj_leader_tf.getText() != "" && fio_buh_tf.getText() != "") {
                vn_cod = vn_cod_tf.getText()
                while (vn_cod.length != 8) {
                    vn_cod = "0" + vn_cod
                }
                if (vn_cod_tf.getText().toInt() < 87000) {
                    bank = "*confidential*"
                } else {
                    bank = "*confidential*"
                }
                creator.createSetup(naim_tf.getText(), fio_leader_tf.getText(), fio_buh_tf.getText(), dolj_leader_tf.getText(), unp_tf.getText(), adress_tf.getText(), vn_cod, bank, vn_cod_tf.getText(), pathForZipArchive)

            } else {
                JOptionPane.showMessageDialog(null, "Некоторые поля не заполнены!")
            }
        }
        selectPathButton.addActionListener { e: ActionEvent ->
            val fileChooser = JFileChooser()
            fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            fileChooser.foreground = orangeColor
            fileChooser.dialogTitle = "Select a directory to save archive with setup"
            val result = fileChooser.showSaveDialog(this)
            if (result == JFileChooser.APPROVE_OPTION) {
                pathForZipArchive = fileChooser.selectedFile.path
                createButton.isEnabled = true
            }
        }
    }
}
