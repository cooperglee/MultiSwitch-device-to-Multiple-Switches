/**
 *  Multi Switch with Virtual Tiles for Master Switch
 *
 *  Copyright 2014 Cooper Lee
 *
 *  NOTE - THIS ONLY WORKS WITH ATTRIBUTE TYPE switch and switch[x] (1..9)
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Multi Switch with Virtual Tiles for Master Switch",
    namespace: "ms_w_vts",
    author: "Cooper Lee",
    description: "Use this app with a device that has multiple switches embedded in the device type and it will create virtual switches for each of the various embedded switches.",
    category: "Convenience",
    iconUrl: "http://cdn.flaticon.com/png/256/25823.png",
    iconX2Url: "http://cdn.flaticon.com/png/256/25823.png",
    iconX3Url: "http://cdn.flaticon.com/png/256/25823.png")


preferences {
	page(name: "mainDevice", uninstall: true, install:false)
	page(name: "virtualSwitches", uninstall: true, install:true)
	page(name: "virtualDetails", uninstall: true, install:true)
}

def mainDevice() {
	dynamicPage(name: "mainDevice", title: "Setup virtual app and multi-switch device", nextPage: "virtualDetails", uninstall: true, install:false) {
        section {
            input "master", "capability.switch", multiple: false, required: true, title: "Choose the device with multiple switches", image: "http://cdn.flaticon.com/png/256/61163.png"
            label title: "Assign a name for this virtual tile handler", required: false
            paragraph: "Assign switches to virtual tiles or real switches on next page"
        }
	}
}

def devTemplates(devType) {
    def templates = [ 
                        "base2"		:"Device with 2 switches", 
                        "base9"		:"Device with up to 9 switches", 

						"aeon4"		:"Aeon Power Strip", 

						"iPool1PSV"	:"PE653 - Pool & Spa w/o Booster (Variable speed pump)", // (blower on sw3 lights on sw2
                        "iPool1PSVB":"PE653 - Pool & Spa w/ Booster (Variable speed pump)", // (booster on sw3 lights/blower on sw2
                        
                        "iPool1"	:"PE653 - Pool and 4 Acc (1 speed pump)", 
                        "iPool1H"	:"PE653 - Pool w/Heater and 3 Acc (1 speed pump)", 
                        "iPool1PS"	:"PE653 - Pool & Spa w/o Booster (1 speed pump)", // blower on sw3 lights on sw2
                        "iPool1PSB"	:"PE653 - Pool & Spa w/ Booster (1 speed pump)", // (booster on sw3 lights/blower on sw2
                        
                        "iPool1S"	:"PE653 - Spa Only (1 speed pump)", // blower on sw3 lights on sw2
                        "iPool2S"	:"PE653 - Spa Only (2 speed pump)", // blower on sw4 lights/booster on sw3
                        
                        "iPool2"	:"PE653 - Pool and 3 Acc (2 speed pump)", 
                        "iPool2H"	:"PE653 - Pool w/Heater and 2 Acc (2 speed pump)",  
                        "iPool2PS"	:"PE653 - Pool & Spa w/o Booster  (2 speed pump)", // blower on sw3
                        "iPool2PB"	:"PE653 - Pool w/ Booster and Lights (2 speed pump)", // (booster on sw3 lights on sw4
                    ]
    if(devType) { return devTemplates.getAt(dev_type) } else { return templates }
}


def templates() {
	def templates = [
    	'aeon4': 	[ "switch":"Power Strip", "switch1":"SW Outlet 1", "switch2":"SW Outlet 2", "switch3":"SW Outlet 3", "switch4":"SW Outlet 4", "switch5":"Outlet 1", "switch6":"Outlet 2", "switch7":"All SW Outlets"],
    	'base': 	[ "switch":"switch", "switch1":"Switch 1"],
    	'base2': 	[ "switch":"switch", "switch1":"Switch 1", "switch2":"Switch 2"],
    	'base9': 	[ "switch":"switch", "switch1":"Switch 1", "switch2":"Switch 2", "switch3":"Switch 3", "switch4":"Switch 4", "switch5":"Switch 5", "switch6":"Switch 6", "switch7":"Switch 7", "switch8":"Switch 8", "switch9":"Switch 9"],
    	'iPool1': 	[ "switch1":"Pool Pump", "switch2":"Switch 2", "switch3":"Switch 3", "switch4":"Switch 4", "switch5":"Switch 5"],
    	'iPool1H': 	[ "switch1":"Pool Pump", "switch2":"Switch 2", "switch3":"Switch 3", "switch4":"Switch 4", "switch5":"Pool Heater"],
    	'iPool1PS':	[ "switch1":"Filter Pump", "switch2":"Water Lights", "switch3":"Spa Blower", "switch4":"Pool/Spa", "switch5":"Water Heater"],
    	'iPool1PSB':[ "switch1":"Filter Pump", "switch2":"Spa Blower", "switch3":"Booster Pump", "switch4":"Pool/Spa", "switch5":"Water Heater"],
    	'iPool1PSV':[ "switch1":"Filter Pump", "switch2":"Spa Blower", "switch3":"Booster Pump", "switch4":"Pool/Spa", "switch5":"Water Heater"],
    	'iPool1PSVB':[ "switch1":"Filter Pump", "switch2":"Pool Lights", "switch3":"Spa Blower", "switch4":"Pool/Spa", "switch5":"Water Heater"],
    	'iPool1S':	[ "switch1":"Spa Pump", "switch2":"Spa Lights", "switch3":"Spa Blower", "switch4":"Spa Accessory", "switch5":"Spa Heater"],
    	
        'iPool2': 	[ "switch1":"Pool Pump High", "switch2":"Pool Pump Low", "switch3":"Switch 3", "switch4":"Switch 4", "switch5":"Switch 5"],
    	'iPool2H': 	[ "switch1":"Pool Pump High", "switch2":"Pool Pump Low", "switch3":"Switch 3", "switch4":"Switch 4", "switch5":"Pool Heater"],
    	'iPool2S':	[ "switch1":"Spa Pump High", "switch2":"Spa Pump Low", "switch3":"Spa Lights", "switch4":"Spa Blower", "switch5":"Spa Heater"],
    	'iPool2PS':	[ "switch1":"Filter Pump High", "switch2":"Filter Pump Low", "switch3":"Spa Blower", "switch4":"Pool/Spa", "switch5":"Water Heater"],
    	'iPool2PB':	[ "switch1":"Pool Pump High", "switch2":"Pool Pump Low", "switch3":"Booster Pump", "switch4":"Pool Lights", "switch5":"Water Heater"],


    	'iPoolSpa':	[ "switch1":"Pump", "switch2":"Switch 2", "switch3":"Switch 3", "switch4":"Switch 4", "switch5":"Pool Heater"],
        			]
    if(switch_template) { return templates.getAt(switch_template) } else { return [ "switch":"switch", "switch1":"Switch 1", "switch2":"Switch 2", "switch3":"Switch 3", "switch4":"Switch 4"] }
}


def virtualDetails() {
	unsubscribe()
	syncChildDevices()
	dynamicPage(name: "virtualDetails", title: "Which virtual switches to create for $master.label?", uninstall: true, install:true) {
        
        section {
            input "switch_template", "enum", multiple: false, required: false, refreshAfterSelection:true, options:devTemplates(), title:"Template to use", description: "What device template should to use for $master.label?", image: "http://cdn.flaticon.com/png/256/25823.png"
        }

        section {
            input "switches", "enum", multiple: true, required: false, refreshAfterSelection:true, options:templates(), title:"Select Switches", description: "Choose which switches of $master.label you would like to have as separate switches", image: "http://cdn.flaticon.com/png/256/25823.png"
        }

        section("Current Virtual Switches of $master.label:") {
            def kids = getChildDevices()
            kids.each { paragraph "$it.label" }
        }
	}
}



def installed() {
	syncChildDevices()
    def kids = getChildDevices()
}

def updated() {
	syncChildDevices()
    def kids = getChildDevices()
    unsubscribe()
	kids.each { subscribe(master, "$it.name", vswitch) }
}

def uninstalled() {
	removeChildDevices()	
}

def vswitch(evt) { 
    def vswitch = evt.descriptionText.find(/switch./).replaceAll(" ","")
//	log.debug "child switch to change: $vswitch event: $evt.value  DDNI:${ddni(vswitch)}"
    def vkid = getChildDevice(ddni(vswitch))
//    log.debug "$evt.value $evt.deviceId $evt.description $evt.descriptionText"
//    log.debug "vkid: $vkid"
	vkid."${evt.value}"()
}
    
//def mswitch(evt) { log.debug "$evt.value $evt.deviceId $evt.description $evt.descriptionText" }

def on(childDevice) {
	def num = childDevice.device.name.replaceAll("switch","")
	if(num) { master."on${num}"() } else { master.on();log.debug "No switch number provided for on" }
}

def off(childDevice) {
	def num = childDevice.device.name.replaceAll("switch","")
	if(num) { master."off${num}"() } else { master.off();log.debug "No switch number provided for off" }
}


private syncChildDevices() {    
    switches.each { def e = getChildDevice(ddni(it)); if(!e) { createChild(it) } }
    def switchKids = getChildDevices()
    def removeKids = switchKids.name - switches
//    log.debug "SWs: $switches del: $removeKids kids: $removeKids"
    removeKids.each { rem ->
    	def delKid = getChildDevice(ddni(rem))
        log.debug delKid.deviceNetworkId
        removeChildDevices(delKid)
        }
}

private createChild(vt) {
		def label = templates().getAt(vt)
		log.debug "Label $label"
		def addedvt = addChildDevice("ms_w_vts", "vTile_ms", ddni(vt), null, [name:vt, label:label, completedSetup: true])
      	log.info "created Virtual Switch ${addedvt.displayName} with DNI ${addedvt.deviceNetworkId}"
}

private removeChildDevices(delete) {
	unsubscribe()
	if(!delete) { delete = getChildDevices() }
    delete.each {
        deleteChildDevice(it.deviceNetworkId)
      	log.debug "deleted ${delete.displayName}"
    }
}

private ddni(id){
	if(!state.appId) { state.appId = app.id }
	def ddni = state.appId + "/" + id
    return ddni
}





