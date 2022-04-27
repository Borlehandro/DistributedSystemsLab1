package me.borlehandro.distributed_systems.lab1.dao

import java.io.InputStream
import javax.xml.namespace.QName
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.events.StartElement

class ParsedXmlData(
    val userChanges: Map<String, Set<String>>,
    val tagUsages: Map<String, Int>,
)

class XmlParser {

    companion object {

        val keyTag: QName = QName("k")
        val tagTag: QName = QName("tag")
        val nodeTag: QName = QName("node")
        val userTag: QName = QName("user")
        val changeSetTag: QName = QName("changeset")
    }

    fun parseXml(input: InputStream?): ParsedXmlData {

        val inputFactory = XMLInputFactory.newInstance()
        val reader = inputFactory.createXMLEventReader(input)
        val parentElements = ArrayDeque<StartElement>()

        val userChanges = mutableMapOf<String, MutableSet<String>>()
        val tagUsages = mutableMapOf<String, Int>()

        while (reader.hasNext()) {
            val nextEvent = reader.nextEvent()
            if (nextEvent.isStartElement) {
                val element = nextEvent.asStartElement()

                if (element.isNode()) {
                    handleNode(element, userChanges)
                } else if (element.isTag()) {
                    handleTag(element, parentElements, tagUsages)
                }

                parentElements.addLast(element)
            } else if (nextEvent.isEndElement) {
                parentElements.removeLast()
            }
        }
        reader.close()
        return ParsedXmlData(userChanges.toMap(), tagUsages.toMap())
    }

    private fun handleNode(
        element: StartElement,
        userChanges: MutableMap<String, MutableSet<String>>
    ) {
        val user = element.getAttributeByName(userTag)
        val changeSet = element.getAttributeByName(changeSetTag)
        if (user == null || changeSet == null) {
            return
        }
        userChanges.putIfAbsent(user.value, mutableSetOf())
        userChanges[user.value]!!.add(changeSet.value)
    }

    private fun handleTag(
        element: StartElement,
        parentElements: ArrayDeque<StartElement>,
        tagUsages: MutableMap<String, Int>
    ) {
        val key = element.getAttributeByName(keyTag)
        val parent = parentElements.last()
        if (!parent.isNode() || key == null) {
            return
        }
        tagUsages.putIfAbsent(key.value, 0)
        tagUsages[key.value] = tagUsages[key.value]!! + 1
    }

    private fun StartElement.isNode() = name == nodeTag

    private fun StartElement.isTag() = name == tagTag
}
